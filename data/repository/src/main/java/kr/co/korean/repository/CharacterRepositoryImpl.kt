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
import kr.co.korean.network.model.CharactersResult
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.repository.model.CharacterDetailDataModel
import kr.co.korean.work.ImageDownLoadResult
import kr.co.korean.work.ThumbnailDownloadDataSource
import org.w3c.dom.CharacterData
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

fun convertDataModel(pagingData: PagingData<CharactersResult>) =
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

fun convertDataModel(remoteData: CharactersResult) =
    CharacterDetailDataModel(
        id = remoteData.id,
        thumbnail = remoteData.thumbnail.imageFullPath,
        name = remoteData.name,
        description = remoteData.description,
        contents = mapOf(
            CharacterDetailDataModel.ContentsType.Series to remoteData.series.items.map { it.name },
            CharacterDetailDataModel.ContentsType.Comics to remoteData.comics.items.map { it.name },
            CharacterDetailDataModel.ContentsType.Stories to remoteData.stories.items.map { it.name },
            CharacterDetailDataModel.ContentsType.Events to remoteData.events.items.map { it.name },
        )
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

    override fun getRemoteSingleCharacter(id: Int): Flow<List<CharacterDetailDataModel>> {
        val currentTimeMillis = System.currentTimeMillis()
        return flow {
            emit(
                value = marvelCharacterApi.getSingleCharacter(
                    id = id,
                    apiKey = BuildConfig.marblePubKey,
                    timeStamp = currentTimeMillis,
                    hash = encodeToMd5("${currentTimeMillis}${BuildConfig.marblePrivKey}${BuildConfig.marblePubKey}"),
                ).data.results.map(::convertDataModel)
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