package kr.co.korean.investment.ui.permission

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import kr.co.korean.common.model.Result

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionEffect(
    multiplePermissionsState: MultiplePermissionsState,
    appFirstStartedState: Boolean,
    onPermissionStateChanged: (Boolean) -> Unit,
    onShowFirst: () -> Unit = {},
    onUserNagativeClickFirst: () -> Unit = {},
    onUserNagativeClickSecond: () -> Unit = {}
) {
    multiplePermissionsState.permissions.forEach { permissionState ->
        LaunchedEffect(permissionState.status) {
            if (multiplePermissionsState.allPermissionsGranted) {
//                multiplePermissionsState = true
                onPermissionStateChanged(true)
            } else {

                // 앱 첫 진입 및 권한 다이얼로그 로딩
                Log.e("permission", "appFirstStartedState : $multiplePermissionsState")
                if (!appFirstStartedState) {
                    Log.e("permission", "first")
                    onShowFirst()
//                    viewModel.startApp()
//                    requiredPermissionsStates.launchMultiplePermissionRequest()
                } else {
                    // 권한 거절 클릭
                    if (permissionState.status.shouldShowRationale) {
                        // 첫 번째 권한 거절로, 권한 필요 다이얼로그를 로딩하며, 권한 재요청
                        onUserNagativeClickFirst()
//                        requiredPermissionsStates.launchMultiplePermissionRequest()
//                        viewModel.showPermissionNeededDialog()
                        Log.e("permission", "shouldShowRationale true")
                    } else {
                        onUserNagativeClickSecond()
                        // 두 번째 권한 거절로, 권한 설정 페이지 리다이렉트 다이얼로그 로딩
//                        viewModel.showPermissionSettingRedirectDialog()
                        Log.e("permission", "shouldShowRationale false")
                    }
                }
            }
        }
    }

}