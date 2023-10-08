package kr.co.korean.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .background(MaterialTheme.colorScheme.secondary)
        .fillMaxSize())
}