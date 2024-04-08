package kr.co.architecture.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.co.architecture.app.ui.LifecycleEffect
import kr.co.architecture.app.ui.navigation.BaseNavHost
import kr.co.architecture.app.ui.navigation.BaseNavigationBarWithItems
import kr.co.architecture.app.ui.navigation.BaseNavigationRails
import kr.co.architecture.app.ui.permission.PermissionDialog
import kr.co.architecture.app.ui.permission.PermissionState
import kr.co.architecture.app.ui.permission.isPermissionAllGranted
import kr.co.architecture.app.ui.permission.permissions
import kr.co.architecture.app.ui.permission.startAppSettingsActivity
import kr.co.architecture.app.ui.theme.BaseTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val uiScope = rememberCoroutineScope()
                val windowSize = calculateWindowSizeClass(this)
                val shouldShowBottomBar = windowSize.widthSizeClass == WindowWidthSizeClass.Compact
                val shouldShowNavRail = !shouldShowBottomBar
                var permissionGrantedState by remember { mutableStateOf(PermissionState.NotYet) }
                val launcherMultiplePermissions = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissionsMap ->
                    if (permissionsMap.values.isNotEmpty()) {
                        val areGranted =
                            permissionsMap.values.reduce { acc, next -> acc && next }
                        permissionGrantedState =
                            if (areGranted) PermissionState.Granted else PermissionState.Rejected
                    }
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            BaseNavigationBarWithItems(navController)
                        }
                    }
                ) { innerPadding ->
                    when (permissionGrantedState) {
                        PermissionState.NotYet -> { }
                        PermissionState.Rejected -> {
                            PermissionDialog(
                                titleText = stringResource(id = R.string.permissionGrantGuide),
                                onClickOk = ::startAppSettingsActivity)
                        }
                        PermissionState.Granted -> {
                            Row {
                                if (shouldShowNavRail) {
                                    BaseNavigationRails(navController)
                                }
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

                SideEffect {
                    if (permissionGrantedState == PermissionState.NotYet) {
                        launcherMultiplePermissions.launch(permissions)
                    }
                }

                val context = LocalContext.current
                LifecycleEffect(
                    onResume = {
                        if (context.isPermissionAllGranted) {
                            permissionGrantedState = PermissionState.Granted
                        }
                    }
                )
            }
        }
    }
}