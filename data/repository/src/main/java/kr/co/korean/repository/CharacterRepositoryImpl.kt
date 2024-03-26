package kr.co.korean.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kr.co.korean.common.encodeToMd5
import kr.co.korean.database.dao.MarvelCharacterDao
import kr.co.korean.database.entity.MarvelCharacter
import kr.co.korean.network.BuildConfig
import kr.co.korean.network.CHARACTER_DATA_PAGE_SIZE
import kr.co.korean.network.MarvelCharacterApi
import kr.co.korean.network.MarvelCharacterPagingSource
import kr.co.korean.network.model.Result
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.work.ImageDownLoadResult
import kr.co.korean.work.ThumbnailDownloadDataSource
import javax.inject.Inject

fun CharacterDataModel.convertRoomModel() =
    MarvelCharacter(
        characterId = id,
        thumbnail = thumbnail,
        name = name,
        description = description,
        urlCount = urlCount,
        comicCount = comicCount,
        storyCount = storyCount,
        seriesCount = seriesCount,
        eventCount = eventCount
    )

fun convertDataModel(roomModels: List<MarvelCharacter>) =
    roomModels.map { marvelCharacter ->
        CharacterDataModel(
            id = marvelCharacter.characterId,
            thumbnail = marvelCharacter.thumbnail,
            name = marvelCharacter.name,
            description = marvelCharacter.description,
            urlCount = marvelCharacter.urlCount,
            comicCount = marvelCharacter.comicCount,
            storyCount = marvelCharacter.storyCount,
            seriesCount = marvelCharacter.seriesCount,
            eventCount = marvelCharacter.eventCount
        )
    }

fun convertDataModel(pagingData: PagingData<Result.CharactersResult>) =
    pagingData.map { pagingData ->
        CharacterDataModel(
            id = pagingData.id,
            thumbnail = pagingData.thumbnail.imageFullPath,
            name = pagingData.name,
            description = pagingData.description,
            urlCount = pagingData.urls.size,
            comicCount = pagingData.comics.returned,
            seriesCount = pagingData.series.returned,
            storyCount = pagingData.stories.returned,
            eventCount = pagingData.events.returned
        )
    }

fun convertDataModel(remoteData: Result.CharactersResult) =
    CharacterDataModel(
        id = 0,
        thumbnail = "",
        name = "",
        description = remoteData.description,
        urlCount = 0,
        comicCount = 0,
        seriesCount = 0,
        storyCount = 0,
        eventCount = 0,
    )

/**
 * Repository의 경우, API의 'XXXResponse' 네이밍의 모델 수신 후,
 * 상위 레이어로 전달을 위한 모델('XXXDataModel')로 파싱 및 Ui Layer or Data Layer에 노출합니다.
 */
class CharacterRepositoryImpl @Inject constructor(
    private val marvelCharacterDao: MarvelCharacterDao,
    private val marvelCharacterApi: MarvelCharacterApi,
    private val thumbnailDownloadDataSource: ThumbnailDownloadDataSource
): CharacterRepository {

    override val localCharacters: Flow<List<CharacterDataModel>> =
        marvelCharacterDao.getAllCharacter()
            .map(::convertDataModel)

    override val remoteCharacters: Flow<PagingData<CharacterDataModel>> =
        Pager(
            config = PagingConfig(pageSize = CHARACTER_DATA_PAGE_SIZE),
            pagingSourceFactory = { MarvelCharacterPagingSource(marvelCharacterApi) }
        ).flow.map(::convertDataModel)

    override suspend fun getRemoteSingleContent(id: Int, type: String): Flow<List<CharacterDataModel>> {
        return flow {
            emit(
                value = marvelCharacterApi.getSpecificContents(
                    apiKey = BuildConfig.marblePubKey,
                    timeStamp = System.currentTimeMillis(),
                    hash = encodeToMd5("${System.currentTimeMillis()}${BuildConfig.marblePrivKey}${BuildConfig.marblePubKey}"),
                    id = id,
                    type = type
                ).data.results
                    .map { it as Result.CharactersResult }
                    .map(::convertDataModel)
            )
        }
    }


    override suspend fun modifyCharacterSavedStatus(dataModel: CharacterDataModel, saved: Boolean) {
        val roomModel = dataModel.convertRoomModel()
        if (saved) {
            marvelCharacterDao.upsert(roomModel)
        } else {
            marvelCharacterDao.deleteCharacter(roomModel.characterId)
        }
    }

    override val imageDownloadState: Flow<ImageDownLoadResult> =
        thumbnailDownloadDataSource.imageDownloadState

    override fun downloadThumbnail(url: String) {
        thumbnailDownloadDataSource.downloadThumbnail(url)
    }
}