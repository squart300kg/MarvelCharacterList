package kr.co.korean.network.model

import kr.co.korean.network.model.common.Characters
import kr.co.korean.network.model.common.Comics
import kr.co.korean.network.model.common.Creators
import kr.co.korean.network.model.common.Item
import kr.co.korean.network.model.common.Series
import kr.co.korean.network.model.common.Stories
import kr.co.korean.network.model.common.Thumbnail
import kr.co.korean.network.model.common.TypeItem
import kr.co.korean.network.model.common.Url

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


            data class Next(
                val name: String,
                val resourceURI: String
            )

            data class Previous(
                val name: String,
                val resourceURI: String
            )
        }
    }
}