package kr.co.korean.ui.model

import androidx.annotation.DrawableRes
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