package kr.co.korean.investment.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.co.korean.investment.R

val baseDestinations: List<BaseDestination> =
    BaseDestination.values().asList()

enum class BaseDestination(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    @StringRes val iconTextIdRes: Int,
) {
    HOME(
        selectedIconRes = R.drawable.ic_tab_home_filled,
        unselectedIconRes = R.drawable.ic_tab_home,
        iconTextIdRes = R.string.tabHome,
    ),
    BOOKMARKS(
        selectedIconRes = R.drawable.ic_tab_bookmark_filled,
        unselectedIconRes = R.drawable.ic_tab_bookmark,
        iconTextIdRes = R.string.tabBookMark,
    ),
}
