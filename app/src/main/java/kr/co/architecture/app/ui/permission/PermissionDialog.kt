package kr.co.architecture.app.ui.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kr.co.architecture.app.R

internal fun Context.startAppSettingsActivity() {
    Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    }.let(::startActivity)
}

@Composable
internal fun PermissionDialog(
    modifier: Modifier = Modifier,
    titleText: String,
    onClickOk: () -> Unit = {},
    onClickCancel: () -> Unit = {}
) {
    Dialog(onDismissRequest = onClickCancel) {
        Surface(
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight(),
        ) {
            Column(modifier = Modifier.width(350.dp)) {

                Text(
                    text = titleText,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp),
                )

                Divider(
                    color = Color.Transparent,
                    modifier = Modifier
                        .height(1.dp + 24.dp)
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )

                Text(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .clickable { onClickOk() },
                    text = stringResource(id = R.string.ok),
                    textAlign = TextAlign.Center,

                    )
            }
        }
    }
}