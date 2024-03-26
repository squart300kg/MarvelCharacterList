package kr.co.korean.network.model

import com.google.gson.annotations.SerializedName
import kr.co.korean.network.constant.ImageScaleType
import kr.co.korean.network.model.common.Comics
import kr.co.korean.network.model.common.Events
import kr.co.korean.network.model.common.Series
import kr.co.korean.network.model.common.Stories
import kr.co.korean.network.model.common.Thumbnail
import kr.co.korean.network.model.common.TypeItem
import kr.co.korean.network.model.common.Url

data class CharactersResponseModel(
    @SerializedName("attributionHTML")
    val attributionHTML: String,
    @SerializedName("attributionText")
    val attributionText: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("data")
    val data: Data,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("status")
    val status: String,
) {
    data class Data(
        @SerializedName("count")
        val count: Int,
        @SerializedName("limit")
        val limit: Int,
        @SerializedName("offset")
        val offset: Int,
        @SerializedName("results")
        val results: List<Result>,
        @SerializedName("total")
        val total: Int
    ) {
        data class Result(
            @SerializedName("comics")
            val comics: Comics,
            @SerializedName("description")
            val description: String,
            @SerializedName("events")
            val events: Events,
            @SerializedName("id")
            val id: Int,
            @SerializedName("modified")
            val modified: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("resourceURI")
            val resourceURI: String,
            @SerializedName("series")
            val series: Series,
            @SerializedName("stories")
            val stories: Stories,
            @SerializedName("thumbnail")
            val thumbnail: Thumbnail,
            @SerializedName("urls")
            val urls: List<Url>
        )
    }
}