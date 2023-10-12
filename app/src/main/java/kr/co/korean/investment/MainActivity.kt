package kr.co.korean.investment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.co.korean.common.model.Result
import kr.co.korean.investment.ui.MainViewModel
import kr.co.korean.investment.ui.navigation.BaseNavHost
import kr.co.korean.investment.ui.navigation.BaseNavigationBarItem
import kr.co.korean.investment.ui.navigation.baseDestinations
import kr.co.korean.investment.ui.navigation.util.getCurrentDestination
import kr.co.korean.investment.ui.navigation.util.isTopLevelDestinationInHierarchy
import kr.co.korean.investment.ui.permission.PermissionEffect
import kr.co.korean.investment.ui.theme.KoreanInvestmentTheme

// TODO: 기획서 추가 리스트
//  0. 플러그인도 카탈로그로 관리하기
//  1. 네트워크 상태 끊겼을 때?
//  2.

val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
    listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
} else {
//    emptyList()
    // TODO: 테스트 완료 후 해당 퍼미션 지워야 함
    listOf(Manifest.permission.POST_NOTIFICATIONS)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val requiredPermissionsStates = rememberMultiplePermissionsState(REQUIRED_PERMISSIONS)
            var permissionGrantedState by remember { mutableStateOf(false) }
            val appFirstStartedState by viewModel.appFirstStartedState.collectAsStateWithLifecycle()
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
                    if (permissionGrantedState) {
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

                when (appFirstStartedState) {
                    is Result.Loading -> {}
                    is Result.Error -> {}
                    is Result.Success -> {
                        PermissionEffect(
                            multiplePermissionsState = requiredPermissionsStates,
                            appFirstStartedState = (appFirstStartedState as Result.Success<Boolean>).model,
                            onPermissionStateChanged = { permissionGrantedState = true },
                            onShowFirst = {
                                viewModel.startApp()
                                requiredPermissionsStates.launchMultiplePermissionRequest()
                            },
                            onUserNagativeClickFirst = {
                                viewModel.showPermissionNeededDialog()
                                requiredPermissionsStates.launchMultiplePermissionRequest()
                            },
                            onUserNagativeClickSecond = {
                                viewModel.showPermissionSettingRedirectDialog()
                            }
                        )
                    }
                }
            }
        }
    }
}