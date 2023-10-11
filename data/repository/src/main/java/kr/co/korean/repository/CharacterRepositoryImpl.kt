package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kr.co.korean.common.encodeToMd5
import kr.co.korean.datastore.MarvelCharacterDataStore
import kr.co.korean.network.BuildConfig
import kr.co.korean.network.MarvelCharacterApi
import kr.co.korean.network.MarvelCharacterPagingSource
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.repository.model.SavedIdsDataModel
import javax.inject.Inject

// TODO: etag연동
/**
 * Repository의 경우, API의 'XXXResponse' 네이밍의 모델 수신 후,
 * 상위 레이어로 전달을 위한 모델('XXXDataModel')로 파싱 및 Ui Layer or Data Layer에 노출합니다.
 */
class CharacterRepositoryImpl @Inject constructor(
    private val marvelCharacterApi: MarvelCharacterApi,
    private val marvelCharacterDataStore: MarvelCharacterDataStore,
    private val marvelCharacterPagingDataSource: MarvelCharacterPagingSource
): CharacterRepository {

    override val localCharacters: Flow<SavedIdsDataModel> =
        marvelCharacterDataStore.savedCharacters.map {
            SavedIdsDataModel(ids = it.idsList)
        }

//    val remoteCharacters2: Flow<CharactersDataModel2> =
//        marvelCharacterPagingDataSource.

    // TODO: 비즈니스로직 분리할지?
    override val remoteCharacters: Flow<List<CharacterDataModel>> =
        flow {
            val currentTimeMillis = System.currentTimeMillis()
            marvelCharacterApi.getCharacters(
                apiKey = BuildConfig.marblePubKey,
                timeStamp = currentTimeMillis,
                hash = encodeToMd5("${currentTimeMillis}${BuildConfig.marblePrivKey}${BuildConfig.marblePubKey}"),
                offset = 2
            ).let { responseModel ->
                responseModel.data.results.map { result ->
                    CharacterDataModel(
                        id = result.id,
                        thumbnail = result.thumbnail.imageFullPath,
                        urlCount = result.urls.size,
                        comicCount = result.comics.returned,
                        seriesCount = result.series.returned,
                        storyCount = result.stories.returned,
                        eventCount = result.events.returned
                    )
                }
            }.let { dataModel ->
                emit(dataModel)
            }
        }

    override suspend fun modifyCharacterSavedStatus(id: Int) {
        marvelCharacterDataStore.setCharacterSaved(id)
    }

}