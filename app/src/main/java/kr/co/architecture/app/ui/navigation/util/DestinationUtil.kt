package kr.co.architecture.app.ui.navigation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kr.co.architecture.app.ui.navigation.BaseDestination

@Composable
fun NavHostController.getCurrentDestination(): NavDestination? {
    return this.currentBackStackEntryAsState()
        .value
        ?.destination
}

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BaseDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false