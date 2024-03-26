package kr.co.korean.network.model

import kr.co.korean.network.model.common.Characters
import kr.co.korean.network.model.common.Comics
import kr.co.korean.network.model.common.Creators
import kr.co.korean.network.model.common.Events
import kr.co.korean.network.model.common.Stories
import kr.co.korean.network.model.common.Thumbnail
import kr.co.korean.network.model.common.Url

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
        )
    }
}