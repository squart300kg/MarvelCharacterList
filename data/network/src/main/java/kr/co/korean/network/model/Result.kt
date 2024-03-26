package kr.co.korean.network.model

import com.google.gson.annotations.SerializedName

sealed interface Result {
    data class CharactersResult(
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
    ): Result

    data class ComicsResult(
        @SerializedName("characters")
        val characters: Characters,
        @SerializedName("collectedIssues")
        val collectedIssues: List<Any>,
        @SerializedName("collections")
        val collections: List<Any>,
        @SerializedName("creators")
        val creators: Creators,
        @SerializedName("dates")
        val dates: List<Date>,
        @SerializedName("description")
        val description: String,
        @SerializedName("diamondCode")
        val diamondCode: String,
        @SerializedName("digitalId")
        val digitalId: Int,
        @SerializedName("ean")
        val ean: String,
        @SerializedName("events")
        val events: Events,
        @SerializedName("format")
        val format: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("images")
        val images: List<Image>,
        @SerializedName("isbn")
        val isbn: String,
        @SerializedName("issn")
        val issn: String,
        @SerializedName("issueNumber")
        val issueNumber: Int,
        @SerializedName("modified")
        val modified: String,
        @SerializedName("pageCount")
        val pageCount: Int,
        @SerializedName("prices")
        val prices: List<Price>,
        @SerializedName("resourceURI")
        val resourceURI: String,
        @SerializedName("series")
        val series: Series,
        @SerializedName("stories")
        val stories: Stories,
        @SerializedName("textObjects")
        val textObjects: List<TextObject>,
        @SerializedName("thumbnail")
        val thumbnail: Thumbnail,
        @SerializedName("title")
        val title: String,
        @SerializedName("upc")
        val upc: String,
        @SerializedName("urls")
        val urls: List<Url>,
        @SerializedName("variantDescription")
        val variantDescription: String,
        @SerializedName("variants")
        val variants: List<Any>
    ): Result {

        data class Date(
            @SerializedName("date")
            val date: String,
            @SerializedName("type")
            val type: String
        )

        data class Image(
            @SerializedName("extension")
            val extension: String,
            @SerializedName("path")
            val path: String
        )

        data class Price(
            @SerializedName("price")
            val price: Double,
            @SerializedName("type")
            val type: String
        )

        data class TextObject(
            @SerializedName("language")
            val language: String,
            @SerializedName("text")
            val text: String,
            @SerializedName("type")
            val type: String
        )
    }

    data class EventsResult(
        val characters: Characters,
        val comics: Comics,
        val creators: Creators,
        val description: String,
        val end: String,
        val id: Int,
        val modified: String,
        val next: Next,
        val previous: Previous,
        val resourceURI: String,
        val series: Series,
        val start: String,
        val stories: Stories,
        val thumbnail: Thumbnail,
        val title: String,
        val urls: List<Url>
    ): Result {
        data class Next(
            val name: String,
            val resourceURI: String
        )
        data class Previous(
            val name: String,
            val resourceURI: String
        )
    }

    data class StoriesResult(
        val characters: Characters,
        val comics: Comics,
        val creators: Creators,
        val description: String,
        val events: Events,
        val id: Int,
        val modified: String,
        val originalIssue: OriginalIssue,
        val resourceURI: String,
        val series: Series,
        val thumbnail: Any,
        val title: String,
        val type: String
    ): Result {
        data class OriginalIssue(
            val name: String,
            val resourceURI: String
        )
    }

    data class SeriesResult(
        val characters: Characters,
        val comics: Comics,
        val creators: Creators,
        val description: String,
        val endYear: Int,
        val events: Events,
        val id: Int,
        val modified: String,
        val next: Any,
        val previous: Any,
        val rating: String,
        val resourceURI: String,
        val startYear: Int,
        val stories: Stories,
        val thumbnail: Thumbnail,
        val title: String,
        val type: String,
        val urls: List<Url>
    ): Result
}