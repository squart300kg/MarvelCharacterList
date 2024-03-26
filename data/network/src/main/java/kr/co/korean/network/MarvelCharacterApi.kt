package kr.co.korean.network

import kr.co.korean.network.model.common.ResponseModel
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

    @GET("/v1/public/characters/{id}/{contentsType}")
    suspend fun getSpecificContents(
        @Path("id") id: Int,
        @Path("contentsType") type: String,
        @Query("apikey") apiKey: String,
        @Query("ts") timeStamp: Long,
        @Query("hash") hash: String,
    ): ResponseModel

}