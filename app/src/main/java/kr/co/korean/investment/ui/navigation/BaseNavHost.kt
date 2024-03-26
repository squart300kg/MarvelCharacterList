package kr.co.korean.investment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kr.co.korean.ui.bookmark.BookmarkScreen
import kr.co.korean.ui.bookmark.navigation.bookmarkScreen
import kr.co.korean.ui.detail.navigateToCharacterDetailScreen
import kr.co.korean.ui.home.HomeScreen
import kr.co.korean.ui.home.navigation.HOME_ROUTE_BASE
import kr.co.korean.ui.home.navigation.homeScreen

@Composable
fun BaseNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onSnackBarStateChanged: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_ROUTE_BASE,
    ) {
        homeScreen(
            onSnackBarStateChanged = onSnackBarStateChanged,
            onNavigateToCharacterDetail = navController::navigateToCharacterDetailScreen
        )
//        composable(route = BaseDestination.HOME.name) {
//            HomeScreen(
//                onSnackBarStateChanged = onSnackBarStateChanged,
//                onNavigateToCharacterDetail = navController::navigateToCharacterDetailScreen
//            )
//        }
        bookmarkScreen()
//        composable(route = BaseDestination.BOOKMARKS.name) {
//            BookmarkScreen()
//        }

    }
}