package kr.co.korean.ui.home

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import kr.co.korean.ui.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val characterUiState by viewModel.characters.collectAsStateWithLifecycle()
    LazyColumn(modifier = modifier) {
        items(characterUiState) { uiModel ->
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        modifier = Modifier.size(90.dp),
                        painter = rememberAsyncImagePainter(uiModel.thumbnail),
                        contentDescription = null
                    )

                    Column {
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemUrlCount) + uiModel.urlCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemComicCount) + uiModel.comicCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemStoryCount) + uiModel.storyCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemEventCount) + uiModel.eventCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemSeriesCount) + uiModel.seriesCount.toString())
                    }
                }
            }
        }

    }
}

@Composable
fun CharacterData(
    modifier: Modifier = Modifier,
    data: String,
) {
    Text(
        modifier = modifier,
        text = data,

    )
}