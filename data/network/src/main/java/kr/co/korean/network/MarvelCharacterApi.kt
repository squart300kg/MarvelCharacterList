package kr.co.korean.network

import kr.co.korean.network.model.CharactersResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelCharacterApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timeStamp: Long,
        @Query("hash") hash: String,
    ): CharactersResponseModel

}