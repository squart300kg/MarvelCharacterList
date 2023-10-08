package kr.co.korean.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val characterUiState by viewModel.characters.collectAsStateWithLifecycle()
    LazyColumn(modifier = modifier) {
        items(characterUiState) { uiModel ->
            Box {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(uiModel.thumbnail),
                        contentDescription = null
                    )
                }
            }
        }

    }

}