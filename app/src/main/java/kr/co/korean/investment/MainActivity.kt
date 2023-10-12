package kr.co.korean.investment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import kr.co.korean.investment.ui.MainViewModel
import kr.co.korean.investment.ui.navigation.BaseNavHost
import kr.co.korean.investment.ui.navigation.BaseNavigationBarItem
import kr.co.korean.investment.ui.navigation.BaseDestination
import kr.co.korean.investment.ui.navigation.baseDestinations
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

    val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val requiredPermissionsStates = rememberMultiplePermissionsState(REQUIRED_PERMISSIONS)
            var permissionGrantedState by remember { mutableStateOf(false) }
            var permissionStartedState by remember { mutableStateOf(false) }

            KoreanInvestmentTheme {
                Scaffold(
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
                    if (permissionGrantedState) {
                        BaseNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }


                requiredPermissionsStates.permissions.forEach { permissionState ->
                    LaunchedEffect(permissionState.status) {
                        if (requiredPermissionsStates.allPermissionsGranted) {
                            permissionGrantedState = true
                        } else {

                            // 앱 첫 진입 및 권한 다이얼로그 로딩
                            if (!permissionStartedState) {
                                Log.e("permission", "first")
                                requiredPermissionsStates.revokedPermissions.forEach {
                                    Log.e("permission", "revoke : ${it.permission}")
                                    Log.e("permission", "revoke : ${it.status.isGranted}")
                                }
                                permissionStartedState = true
                                requiredPermissionsStates.launchMultiplePermissionRequest()
                            } else {
                                // 권한 거절 클릭
                                if (permissionState.status.shouldShowRationale) {
                                    // 첫 번째 권한 거절로, 권한 필요 다이얼로그를 로딩하며, 권한 재요청
                                    requiredPermissionsStates.launchMultiplePermissionRequest()
                                    viewModel.showPermissionNeededDialog()
                                    Log.e("permission", "shouldShowRationale true")
                                } else {
                                    // 두 번째 권한 거절로, 권한 설정 페이지 리다이렉트 다이얼로그 로딩
                                    viewModel.showPermissionSettingRedirectDialog()
                                    Log.e("permission", "shouldShowRationale false")
                                }
                            }
                        }
                    }
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