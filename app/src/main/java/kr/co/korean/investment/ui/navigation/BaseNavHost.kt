package kr.co.korean.investment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kr.co.korean.investment.BookmarkScreen
import kr.co.korean.investment.HomeScreen

@Composable
fun BaseNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BaseDestination.HOME.name,
        modifier = modifier
    ) {
        composable(route = BaseDestination.HOME.name) {
            HomeScreen()
        }
        composable(route = BaseDestination.BOOKMARKS.name) {
            BookmarkScreen()
        }

    }
}