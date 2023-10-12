package kr.co.korean.ui.model

import androidx.annotation.DrawableRes
import kr.co.korean.repository.model.CharacterDataModel
import kr.co.korean.ui.R

data class CharactersUiModel(
    val id: Int,
    val thumbnail: String,
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
        urlCount = dataModel.urlCount,
        comicCount = dataModel.comicCount,
        storyCount = dataModel.storyCount,
        seriesCount = dataModel.seriesCount,
        eventCount = dataModel.eventCount,
        saved = true
    )