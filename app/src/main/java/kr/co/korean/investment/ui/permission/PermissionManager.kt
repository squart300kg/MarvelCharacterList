package kr.co.korean.investment.ui.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

enum class PermissionState {
    NotYet,
    Granted,
    Rejected
}

val permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
} else {
    emptyArray()
}

val Context.isPermissionAllGranted: Boolean
    get() = permissions.all { permission ->
        ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }