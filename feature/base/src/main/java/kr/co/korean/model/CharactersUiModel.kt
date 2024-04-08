package kr.co.korean.model

import androidx.annotation.DrawableRes
import kr.co.korean.home.R
import kr.co.korean.repository.model.CharacterDataModel

data class CharactersUiModel(
    val id: Int,
    val thumbnail: String,
    val name: String,
    val description: String,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
    val saved: Boolean,
) {
    @get:DrawableRes
    val bookMarkImage: Int
        get() = if (saved) R.drawable.ic_bookmark_select_filled
        else R.drawable.ic_bookmark_select
}

fun CharactersUiModel.convertDataModel() =
    CharacterDataModel(
        id = id,
        thumbnail = thumbnail,
        name = name,
        description = description,
        urlCount = urlCount,
        storyCount = storyCount,
        seriesCount = seriesCount,
        eventCount = eventCount,
        comicCount = comicCount
    )


fun convertUiModel(dataModel: CharacterDataModel): CharactersUiModel =
    CharactersUiModel(
        id = dataModel.id,
        thumbnail = dataModel.thumbnail,
        name = dataModel.name,
        description = dataModel.description,
        urlCount = dataModel.urlCount,
        comicCount = dataModel.comicCount,
        storyCount = dataModel.storyCount,
        seriesCount = dataModel.seriesCount,
        eventCount = dataModel.eventCount,
        saved = true
    )