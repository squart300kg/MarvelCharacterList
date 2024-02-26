package kr.co.korean.network.model

import com.google.gson.annotations.SerializedName
import kr.co.korean.network.constant.ImageScaleType

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
        ) {

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

            data class Thumbnail(
                @SerializedName("extension")
                val extension: String,
                @SerializedName("path")
                val path: String
            ) {
                val imageFullPath: String
                    get() = "${path.replace("http", "https")}/${ImageScaleType.LANDSCAPE_XLARGE.value}.$extension"
            }

            data class Url(
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
            )

        }

        data class NormalItem(
            @SerializedName("name")
            val name: String,
            @SerializedName("resourceURI")
            val resourceURI: String
        )

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