package kr.co.korean.investment.ui.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

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
                onPermissionStateChanged(true)
            } else {

                // 앱 첫 진입 및 권한 다이얼로그 로딩
                if (!appFirstStartedState) {
                    onShowFirst()
                } else {
                    // 권한 거절 클릭
                    if (permissionState.status.shouldShowRationale) {
                        // 첫 번째 권한 거절로, 권한 필요 다이얼로그를 로딩하며, 권한 재요청
                        onUserNagativeClickFirst()
                    } else {
                        onUserNagativeClickSecond()
                        // 두 번째 권한 거절로, 권한 설정 페이지 리다이렉트 다이얼로그 로딩
                    }
                }
            }
        }
    }

}