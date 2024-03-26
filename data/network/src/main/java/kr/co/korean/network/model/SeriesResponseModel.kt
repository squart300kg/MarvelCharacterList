package kr.co.korean.network.model

import kr.co.korean.network.model.common.Result

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
        val results: List<Result.SeriesResult>,
        val total: Int
    )
}