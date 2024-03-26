package kr.co.korean.ui.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.korean.ui.detail.DetailScreen

const val DETAIL_ID_ARG = "detailId"
const val DETAIL_ROUTE_BASE = "detailRoute"
const val DETAIL_ROUTE = "$DETAIL_ROUTE_BASE?$DETAIL_ID_ARG={$DETAIL_ID_ARG}"

fun NavHostController.navigateToDetailScreen(id: String?, navOption: NavOptions? = null) {
    val route = if (id != null) {
        "$DETAIL_ROUTE_BASE?$DETAIL_ID_ARG=$id"
    } else {
        DETAIL_ROUTE_BASE
    }
    navigate(route, navOption)
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = DETAIL_ROUTE,
        arguments = listOf(
            navArgument(DETAIL_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }
        )
    ) {
        DetailScreen()
    }
}

