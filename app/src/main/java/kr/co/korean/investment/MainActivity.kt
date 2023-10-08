package kr.co.korean.investment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import kr.co.korean.investment.ui.navigation.BaseNavHost
import kr.co.korean.investment.ui.navigation.BaseNavigationBarItem
import kr.co.korean.investment.ui.navigation.BaseDestination
import kr.co.korean.investment.ui.navigation.baseDestinations
import kr.co.korean.investment.ui.theme.KoreanInvestmentTheme

// TODO: 기획서 추가 리스트
//  0. 플러그인도 카탈로그로 관리하기
//  1. 네트워크 상태 끊겼을 때?
//  2.

@AndroidEntryPoint
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
                            baseDestinations.forEach { destination ->
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
                    BaseNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// TODO: 이 코드 어떻게 위치시킬지?
@Composable
private fun NavHostController.getCurrentDestination(): NavDestination? {
    return this.currentBackStackEntryAsState()
            .value
            ?.destination
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BaseDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false