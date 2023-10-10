package kr.co.korean.repository.model

data class CharacterDataModel(
    val id: String,
    val urlCount: Int,
    val comicCount: Int,
    val seriesCount: Int,
    val storyCount: Int,
    val eventCount: Int,
)