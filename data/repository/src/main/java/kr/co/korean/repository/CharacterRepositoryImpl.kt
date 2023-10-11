package kr.co.korean.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.korean.datastore.MarvelCharacterDataStore
import kr.co.korean.datastore.MarvelCharacters
import kr.co.korean.network.CHARACTER_DATA_PAGE_SIZE
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
    private val marvelCharacterDataStore: MarvelCharacterDataStore,
    private val marvelCharacterPagingDataSource: MarvelCharacterPagingSource
): CharacterRepository {

    override val localCharacters: Flow<SavedIdsDataModel> =
        marvelCharacterDataStore.savedCharacters.map {
            SavedIdsDataModel(ids = it.charactersList.map { it.id } )
        }

    override val remoteCharacters: Flow<PagingData<CharacterDataModel>> =
        Pager(config = PagingConfig(
            pageSize = CHARACTER_DATA_PAGE_SIZE
        )) {
            marvelCharacterPagingDataSource
        }.flow.map { pagingDatas ->
            pagingDatas.map { pagingData ->
                CharacterDataModel(
                    id = pagingData.id,
                    thumbnail = pagingData.thumbnail.imageFullPath,
                    urlCount = pagingData.urls.size,
                    comicCount = pagingData.comics.returned,
                    seriesCount = pagingData.series.returned,
                    storyCount = pagingData.stories.returned,
                    eventCount = pagingData.events.returned
                )
            }
        }

    override suspend fun modifyCharacterSavedStatus(dataModel: CharacterDataModel) {
        MarvelCharacters.newBuilder().defaultInstanceForType.parserForType
        MarvelCharacters(

        )

        marvelCharacterDataStore.setCharacterSaved(

        )
    }

}