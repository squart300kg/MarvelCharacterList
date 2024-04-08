package kr.co.architecture.ui.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.architecture.ui.detail.DetailScreen

const val DETAIL_ID_ARG = "detailId"
const val DETAIL_ROUTE_BASE = "detailRoute"
const val DETAIL_ROUTE = "$DETAIL_ROUTE_BASE?$DETAIL_ID_ARG={$DETAIL_ID_ARG}"

fun NavHostController.navigateToDetailScreen(id: Int?, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    val route = if (id != null) {
        "$DETAIL_ROUTE_BASE?$DETAIL_ID_ARG=$id"
    } else {
        DETAIL_ROUTE_BASE
    }
    navigate(route, navOptions)
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = DETAIL_ROUTE,
        arguments = listOf(
            navArgument(DETAIL_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.IntType
            },
        )
    ) {
        DetailScreen()
    }
}

