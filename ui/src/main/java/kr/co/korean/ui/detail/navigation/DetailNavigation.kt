package kr.co.korean.ui.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.korean.ui.detail.DetailScreen

const val DETAIL_ID_ARG = "detailId"
const val DETAIL_CONTENTS_TYPE_ARG = "contentsType"
const val DETAIL_ROUTE_BASE = "detailRoute"
const val DETAIL_ROUTE = "$DETAIL_ROUTE_BASE?$DETAIL_ID_ARG={$DETAIL_ID_ARG}&$DETAIL_CONTENTS_TYPE_ARG={$DETAIL_CONTENTS_TYPE_ARG}"

fun NavHostController.navigateToDetailScreen(id: Int?, type: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    val route = if (id != null) {
        "$DETAIL_ROUTE_BASE?$DETAIL_ID_ARG=$id&$DETAIL_CONTENTS_TYPE_ARG=$type"
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
            navArgument(DETAIL_CONTENTS_TYPE_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        )
    ) {
        DetailScreen()
    }
}

