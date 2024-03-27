package kr.co.korean.network

import kr.co.korean.network.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelCharacterApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") timeStamp: Long,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
    ): ResponseModel

    @GET("/v1/public/characters/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") timeStamp: Long,
        @Query("hash") hash: String,
    ): ResponseModel

}