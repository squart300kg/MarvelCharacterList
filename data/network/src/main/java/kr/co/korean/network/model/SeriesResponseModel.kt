package kr.co.korean.network.model

import kr.co.korean.network.model.common.Events
import kr.co.korean.network.model.common.Item
import kr.co.korean.network.model.common.TypeItem

data class SeriesResponseModel(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
    val status: String
) {
    data class Data(
        val count: Int,
        val limit: Int,
        val offset: Int,
        val results: List<Result>,
        val total: Int
    ) {
        data class Result(
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
        ) {
            data class Characters(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            )

            data class Comics(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            )

            data class Creators(
                val available: Int,
                val collectionURI: String,
                val items: List<ItemXX>,
                val returned: Int
            ) {
                data class ItemXX(
                    val name: String,
                    val resourceURI: String,
                    val role: String
                )
            }

            data class Stories(
                val available: Int,
                val collectionURI: String,
                val items: List<TypeItem>,
                val returned: Int
            )

            data class Thumbnail(
                val extension: String,
                val path: String
            )

            data class Url(
                val type: String,
                val url: String
            )
        }
    }
}