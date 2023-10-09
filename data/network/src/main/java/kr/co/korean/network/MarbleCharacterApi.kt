package kr.co.korean.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MarbleCharacterApi {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timeStamp: Long,
        @Query("hash") hash: String,
    )

}