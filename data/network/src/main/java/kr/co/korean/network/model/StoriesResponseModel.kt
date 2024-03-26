package kr.co.korean.network.model

import kr.co.korean.network.model.common.Characters
import kr.co.korean.network.model.common.Comics
import kr.co.korean.network.model.common.Events
import kr.co.korean.network.model.common.Item
import kr.co.korean.network.model.common.RoleItem
import kr.co.korean.network.model.common.Series

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

            data class Creators(
                val available: Int,
                val collectionURI: String,
                val items: List<RoleItem>,
                val returned: Int
            )

            data class OriginalIssue(
                val name: String,
                val resourceURI: String
            )

        }
    }
}