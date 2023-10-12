package kr.co.korean.investment.ui.navigation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kr.co.korean.investment.ui.navigation.BaseDestination

@Composable
fun NavHostController.getCurrentDestination(): NavDestination? {
    return this.currentBackStackEntryAsState()
        .value
        ?.destination
}

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BaseDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false