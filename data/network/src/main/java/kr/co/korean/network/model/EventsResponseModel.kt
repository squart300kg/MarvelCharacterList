package kr.co.korean.network.model

data class EventsResponseModel(
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
        ) {

            data class Item(
                val name: String,
                val resourceURI: String
            )

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

            data class Next(
                val name: String,
                val resourceURI: String
            )

            data class Previous(
                val name: String,
                val resourceURI: String
            )

            data class Series(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            )

            data class Stories(
                val available: Int,
                val collectionURI: String,
                val items: List<ItemXXXX>,
                val returned: Int
            ) {
                data class ItemXXXX(
                    val name: String,
                    val resourceURI: String,
                    val type: String
                )
            }

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