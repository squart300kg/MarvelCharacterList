package kr.co.korean.repository.model

// TODO: List를 안에 포함하도록 변경하기
data class CharacterDataModel(
    val id: Int,
    val thumbnail: String,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
)
data class CharactersDataModel2(
    val characters: List<Character>
) {
    data class Character(
        val id: Int,
        val thumbnail: String,
        val urlCount: Int,
        val comicCount: Int,
        val storyCount: Int,
        val eventCount: Int,
        val seriesCount: Int,
    )
}
