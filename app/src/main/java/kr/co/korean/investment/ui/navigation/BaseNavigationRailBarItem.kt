package kr.co.korean.investment.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import kr.co.korean.investment.ui.navigation.util.getCurrentDestination
import kr.co.korean.investment.ui.navigation.util.isTopLevelDestinationInHierarchy

@Composable
internal fun BaseNavigationRails(
    navController: NavHostController
) {
    NavigationRail {
        baseDestinations.forEach { destination ->
            val selected = navController
                .getCurrentDestination()
                .isTopLevelDestinationInHierarchy(destination)

            BaseNavigationRailBarItem(
                onClick = {
                    val topLevelNavOptions = navOptions {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }

                    navController.navigate(destination.route, topLevelNavOptions)
                },
                selected = selected,
                destination = destination
            )

        }
    }
}

@Composable
private fun BaseNavigationRailBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean,
    destination: BaseDestination
) {
    NavigationRailItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = if (selected) {
                    destination.selectedIconRes
                } else {
                    destination.unselectedIconRes
                }),
                contentDescription = null
            )
        },
        label = { Text(stringResource(id = destination.iconTextIdRes)) })
}