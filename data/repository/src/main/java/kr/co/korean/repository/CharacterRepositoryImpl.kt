package kr.co.korean.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.korean.network.BuildConfig
import kr.co.korean.network.MarvelCharacterApi
import kr.co.korean.repository.model.CharacterDataModel
import javax.inject.Inject


class CharacterRepositoryImpl @Inject constructor(
    private val marvelCharacterApi: MarvelCharacterApi,
): CharacterRepository {

    override fun getCharacters(): Flow<CharacterDataModel> {
        return flow {
            val currentTimeMillis = System.currentTimeMillis()
            val response = marvelCharacterApi.getCharacters(
                apiKey = BuildConfig.marblePubKey,
                timeStamp = currentTimeMillis,
                hash = "${currentTimeMillis}${BuildConfig.marblePrivKey}${BuildConfig.marblePubKey}"
            )
            response
            emit(CharacterDataModel(ss = ""))
        }
    }

}