package kr.co.korean.repository.model

data class CharacterDataModel(
    val id: Int,
    val thumbnail: String,
    val name: String,
    val description: String,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
)
