package kr.co.korean.investment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.co.korean.investment.ui.navigation.BaseNavHost
import kr.co.korean.investment.ui.navigation.BaseNavigationBarItem
import kr.co.korean.investment.ui.navigation.baseDestinations
import kr.co.korean.investment.ui.navigation.util.getCurrentDestination
import kr.co.korean.investment.ui.navigation.util.isTopLevelDestinationInHierarchy
import kr.co.korean.investment.ui.theme.KoreanInvestmentTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val uiScope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }

            KoreanInvestmentTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            baseDestinations.forEach { destination ->
                                val selected = navController
                                    .getCurrentDestination()
                                    .isTopLevelDestinationInHierarchy(destination)

                                BaseNavigationBarItem(
                                    onClick = {
                                        val topLevelNavOptions = navOptions {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                            }
                                            launchSingleTop = true
                                        }

                                        navController.navigate(destination.name, topLevelNavOptions) },
                                    selected = selected,
                                    destination = destination
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    BaseNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        onSnackBarStateChanged = { message ->
                            uiScope.launch {
                                snackbarHostState.showSnackbar(message = message)
                            }
                        }
                    )
                }
            }
        }
    }
}