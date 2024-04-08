package kr.co.korean.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

const val DETAIL_PLACEHOLDER_BASE_ROUTE = "detailPlaceHolderRoute"

@Composable
fun DetailPlaceHolderScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "no selected contents",
            style = TextStyle(color = Color.White, fontSize = 16.sp)
        )
    }
}