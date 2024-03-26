package kr.co.korean.network.model

data class StoriesResponseModel(
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
            val events: Events,
            val id: Int,
            val modified: String,
            val originalIssue: OriginalIssue,
            val resourceURI: String,
            val series: Series,
            val thumbnail: Any,
            val title: String,
            val type: String
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

            data class Series(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            )

            data class Item(
                val name: String,
                val resourceURI: String
            )

            data class Creators(
                val available: Int,
                val collectionURI: String,
                val items: List<Item>,
                val returned: Int
            ) {
                data class Item(
                    val name: String,
                    val resourceURI: String,
                    val role: String
                )
            }

            data class Events(
                val available: Int,
                val collectionURI: String,
                val items: List<Any>,
                val returned: Int
            )

            data class OriginalIssue(
                val name: String,
                val resourceURI: String
            )

        }
    }
}