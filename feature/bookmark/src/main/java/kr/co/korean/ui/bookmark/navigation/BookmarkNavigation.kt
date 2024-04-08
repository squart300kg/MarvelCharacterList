package kr.co.korean.ui.bookmark.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.korean.ui.bookmark.BookmarkScreen

const val BOOKMARK_ROUTE_BASE = "bookmarkRoute"

fun NavGraphBuilder.bookmarkScreen() {
    composable(route = BOOKMARK_ROUTE_BASE) {
        BookmarkScreen()
    }
}