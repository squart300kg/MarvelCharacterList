package kr.co.korean.investment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kr.co.korean.investment.ui.navigation.TopLevelDestination
import kr.co.korean.investment.ui.theme.KoreanInvestmentTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            KoreanInvestmentTheme {

                @OptIn(ExperimentalMaterial3Api::class)
                Scaffold(
                    snackbarHost = {},
                    bottomBar = {
                        NavigationBar {
                            TopLevelDestination.values().asList().forEach { destination ->
                                val selected = navController
                                    .getCurrentDestination()
                                    .isTopLevelDestinationInHierarchy(destination)

                                BaseNavigationBarItem(
                                    onClick = {
                                        val topLevelNavOptions = navOptions {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }

                                        navController.navigate(destination.name, topLevelNavOptions) },
                                    selected = selected,
                                    destination = destination
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = TopLevelDestination.HOME.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = TopLevelDestination.HOME.name) {
                            HomeScreen()
                        }
                        composable(route = TopLevelDestination.BOOKMARKS.name) {
                            BookmarkScreen()
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.BaseNavigationBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean,
    destination: TopLevelDestination
) {
    NavigationBarItem(
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
        label = { Text(stringResource(id = destination.iconTextIdRes)) }
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .background(MaterialTheme.colorScheme.primary)
        .fillMaxSize())
}

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .background(MaterialTheme.colorScheme.secondary)
        .fillMaxSize())
}

@Composable
private fun NavHostController.getCurrentDestination(): NavDestination? {
    return this.currentBackStackEntryAsState()
            .value
            ?.destination
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false