package kr.co.korean.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Text(
        modifier = modifier.fillMaxSize(),
        text = uiState.toString())



}