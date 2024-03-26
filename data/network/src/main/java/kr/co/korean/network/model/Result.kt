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
        @SerializedName("characters")
        val characters: Characters,
        @SerializedName("comics")
        val comics: Comics,
        @SerializedName("creators")
        val creators: Creators,
        @SerializedName("description")
        val description: String,
        @SerializedName("end")
        val end: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("modified")
        val modified: String,
        @SerializedName("next")
        val next: Next,
        @SerializedName("previous")
        val previous: Previous,
        @SerializedName("resourceURI")
        val resourceURI: String,
        @SerializedName("series")
        val series: Series,
        @SerializedName("start")
        val start: String,
        @SerializedName("stories")
        val stories: Stories,
        @SerializedName("thumbnail")
        val thumbnail: Thumbnail,
        @SerializedName("title")
        val title: String,
        @SerializedName("urls")
        val urls: List<Url>
    ): Result {
        data class Next(
            @SerializedName("name")
            val name: String,
            @SerializedName("resourceURI")
            val resourceURI: String
        )
        data class Previous(
            @SerializedName("name")
            val name: String,
            @SerializedName("resourceURI")
            val resourceURI: String
        )
    }

    data class StoriesResult(
        @SerializedName("characters")
        val characters: Characters,
        @SerializedName("comics")
        val comics: Comics,
        @SerializedName("creators")
        val creators: Creators,
        @SerializedName("description")
        val description: String,
        @SerializedName("events")
        val events: Events,
        @SerializedName("id")
        val id: Int,
        @SerializedName("modified")
        val modified: String,
        @SerializedName("originalIssue")
        val originalIssue: OriginalIssue,
        @SerializedName("resourceURI")
        val resourceURI: String,
        @SerializedName("series")
        val series: Series,
        @SerializedName("thumbnail")
        val thumbnail: Any,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String
    ): Result {
        data class OriginalIssue(
            @SerializedName("name")
            val name: String,
            @SerializedName("resourceURI")
            val resourceURI: String
        )
    }

    data class SeriesResult(
        @SerializedName("characters")
        val characters: Characters,
        @SerializedName("comics")
        val comics: Comics,
        @SerializedName("creators")
        val creators: Creators,
        @SerializedName("description")
        val description: String,
        @SerializedName("endYear")
        val endYear: Int,
        @SerializedName("events")
        val events: Events,
        @SerializedName("id")
        val id: Int,
        @SerializedName("modified")
        val modified: String,
        @SerializedName("next")
        val next: Any,
        @SerializedName("previous")
        val previous: Any,
        @SerializedName("rating")
        val rating: String,
        @SerializedName("resourceURI")
        val resourceURI: String,
        @SerializedName("startYear")
        val startYear: Int,
        @SerializedName("stories")
        val stories: Stories,
        @SerializedName("thumbnail")
        val thumbnail: Thumbnail,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("urls")
        val urls: List<Url>
    ): Result
}