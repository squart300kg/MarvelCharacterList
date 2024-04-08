package kr.co.architecture.app.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.co.architecture.app.R
import kr.co.architecture.ui.bookmark.navigation.BOOKMARK_ROUTE_BASE
import kr.co.architecture.ui.home.navigation.HOME_ROUTE_BASE

val baseDestinations: List<BaseDestination> =
    BaseDestination.values().asList()

enum class BaseDestination(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    @StringRes val iconTextIdRes: Int,
    val route: String
) {
    HOME(
        selectedIconRes = R.drawable.ic_tab_home_filled,
        unselectedIconRes = R.drawable.ic_tab_home,
        iconTextIdRes = R.string.tabHome,
        route = HOME_ROUTE_BASE
    ),
    BOOKMARKS(
        selectedIconRes = R.drawable.ic_tab_bookmark_filled,
        unselectedIconRes = R.drawable.ic_tab_bookmark,
        iconTextIdRes = R.string.tabBookMark,
        route = BOOKMARK_ROUTE_BASE
    ),
}
