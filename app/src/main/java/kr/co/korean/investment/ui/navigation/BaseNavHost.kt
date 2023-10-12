package kr.co.korean.investment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kr.co.korean.ui.bookmark.BookmarkScreen
import kr.co.korean.ui.home.HomeScreen

@Composable
fun BaseNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onSnackBarStateChanged: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BaseDestination.HOME.name,
    ) {
        composable(route = BaseDestination.HOME.name) {
            HomeScreen(
                onSnackBarStateChanged = onSnackBarStateChanged
            )
        }
        composable(route = BaseDestination.BOOKMARKS.name) {
            BookmarkScreen()
        }

    }
}