package kr.co.korean.network.model

import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class CharactersResponseModel(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
    val status: String,
) {
    data class Data(
        val count: Int,
        val limit: Int,
        val offset: Int,
        val results: List<Result>,
        val total: Int
    ) {
        data class Result(
            val comics: Comics,
            val description: String,
            val events: Events,
            val id: Int,
            val modified: String,
            val name: String,
            val resourceURI: String,
            val series: Series,
            val stories: Stories,
            val thumbnail: Thumbnail,
            val urls: List<Url>
        ) {

            sealed interface Item {

                val name: String
                val resourceURI: String
                data class NormalItem(
                    override val name: String,
                    override val resourceURI: String
                ): Item

                data class StoryItem(
                    override val name: String,
                    override val resourceURI: String,
                    val type: String
                ): Item
            }


            data class Comics(
                val available: Int,
                val collectionURI: String,
                val items: List<Item.NormalItem>,
                val returned: Int
            )

            data class Events(
                val available: Int,
                val collectionURI: String,
                val items: List<Item.NormalItem>,
                val returned: Int
            )

            data class Series(
                val available: Int,
                val collectionURI: String,
                val items: List<Item.NormalItem>,
                val returned: Int
            )

            data class Stories(
                val available: Int,
                val collectionURI: String,
                val items: List<Item.StoryItem>,
                val returned: Int
            )

            data class Thumbnail(
                val extension: String,
                val path: String
            ) {
                val imageFullPath: String
                    get() = "${path.replace("http", "https")}/standard_xlarge.$extension"
            }

            data class Url(
                val type: String,
                val url: String
            )

        }
    }
}