package kr.co.architecture.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.architecture.ui.home.HomeScreen

const val HOME_ROUTE_BASE = "homeRoute"

fun NavGraphBuilder.homeScreen(
    onSnackBarStateChanged: (String) -> Unit,
) {
    composable(route = HOME_ROUTE_BASE) {
        HomeScreen(
            onSnackBarStateChanged = onSnackBarStateChanged,
        )
    }
}