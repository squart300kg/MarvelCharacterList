package kr.co.architecture.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kr.co.architecture.ui.bookmark.navigation.bookmarkScreen
import kr.co.architecture.ui.home.navigation.HOME_ROUTE_BASE
import kr.co.architecture.ui.home.navigation.homeScreen

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
            onSnackBarStateChanged = onSnackBarStateChanged
        )
        bookmarkScreen()
    }
}