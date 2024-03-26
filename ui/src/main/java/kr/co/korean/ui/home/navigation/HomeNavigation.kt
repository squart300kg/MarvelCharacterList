package kr.co.korean.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.korean.ui.home.HomeScreen

const val HOME_ROUTE_BASE = "home_route"

fun NavGraphBuilder.homeScreen(
    onSnackBarStateChanged: (String) -> Unit,
    onNavigateToCharacterDetail: () -> Unit,
) {
    composable(route = HOME_ROUTE_BASE) {
        HomeScreen(
            onSnackBarStateChanged = onSnackBarStateChanged,
            onNavigateToCharacterDetail = onNavigateToCharacterDetail,
        )
    }
}