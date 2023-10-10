package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.korean.network.BuildConfig
import kr.co.korean.network.MarvelCharacterApi
import kr.co.korean.repository.model.CharacterDataModel
import javax.inject.Inject

// TODO: etag연동
/**
 * Repository의 경우, API의 ResponseModel 수신 후,
 * DataModel로 파싱하여 외부 레이어(Ui Layer or Data Layer)에 노출합니다.
 */
class CharacterRepositoryImpl @Inject constructor(
    private val marvelCharacterApi: MarvelCharacterApi,
): CharacterRepository {

    override fun getCharacters(): Flow<List<CharacterDataModel>> {
        return flow {
            val currentTimeMillis = System.currentTimeMillis()
            marvelCharacterApi.getCharacters(
                apiKey = BuildConfig.marblePubKey,
                timeStamp = currentTimeMillis,
                hash = "${currentTimeMillis}${BuildConfig.marblePrivKey}${BuildConfig.marblePubKey}"
            ).let { responseModel ->
                responseModel.data.results.map { result ->
                    CharacterDataModel(
                        id = result.id,
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
    }
}