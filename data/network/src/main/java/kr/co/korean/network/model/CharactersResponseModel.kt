package kr.co.korean.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kr.co.korean.network.constant.ImageScaleType

@Keep
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
    @Keep
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
        @Keep
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
        ) {

            @Keep
            data class Comics(
                @SerializedName("available")
                val available: Int,
                @SerializedName("collectionURI")
                val collectionURI: String,
                @SerializedName("items")
                val items: List<NormalItem>,
                @SerializedName("returned")
                val returned: Int
            )

            @Keep
            data class Events(
                @SerializedName("available")
                val available: Int,
                @SerializedName("collectionURI")
                val collectionURI: String,
                @SerializedName("items")
                val items: List<NormalItem>,
                @SerializedName("returned")
                val returned: Int
            )

            @Keep
            data class Series(
                @SerializedName("available")
                val available: Int,
                @SerializedName("collectionURI")
                val collectionURI: String,
                @SerializedName("items")
                val items: List<NormalItem>,
                @SerializedName("returned")
                val returned: Int
            )

            @Keep
            data class Stories(
                @SerializedName("available")
                val available: Int,
                @SerializedName("collectionURI")
                val collectionURI: String,
                @SerializedName("items")
                val items: List<StoryItem>,
                @SerializedName("returned")
                val returned: Int
            )

            @Keep
            data class Thumbnail(
                @SerializedName("extension")
                val extension: String,
                @SerializedName("path")
                val path: String
            ) {
                val imageFullPath: String
                    get() = "${path.replace("http", "https")}/${ImageScaleType.PORTRAIT_XLARGE.value}.$extension"
            }

            @Keep
            data class Url(
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
            )

        }

        @Keep
        data class NormalItem(
            @SerializedName("name")
            val name: String,
            @SerializedName("resourceURI")
            val resourceURI: String
        )

        @Keep
        data class StoryItem(
            @SerializedName("name")
            val name: String,
            @SerializedName("resourceURI")
            val resourceURI: String,
            @SerializedName("type")
            val type: String
        )
    }
}