package kr.co.korean.ui.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.korean.common.model.UiResult
import kr.co.korean.ui.base.BaseCharacterItem
import kr.co.korean.model.CharactersUiModel
import kr.co.korean.util.DevicePreviews

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val characterUiState by viewModel.localCharacters.collectAsStateWithLifecycle()
    var progressState by remember { mutableStateOf(true) }
    BookmarkScreen(
        modifier = modifier,
        characterUiState = characterUiState,
        progressState = progressState,
        onProgressStateChange = { progressState = it},
        modifyCharacterSavedStatus = viewModel::modifyCharacterSavedStatus
    )
}

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    characterUiState: UiResult<List<CharactersUiModel>>,
    progressState: Boolean,
    onProgressStateChange: (Boolean) -> Unit,
    modifyCharacterSavedStatus: (CharactersUiModel, Boolean) -> Unit
) {
    when (characterUiState) {
        is UiResult.Loading -> onProgressStateChange(true)
        is UiResult.Error -> onProgressStateChange(false)
        is UiResult.Success -> {
            onProgressStateChange(false)

            LazyColumn(modifier = modifier) {
                items(characterUiState.model.size) { index  ->
                    BaseCharacterItem(
                        characterUiState = characterUiState.model[index],
                        onModifyingCharacterSavedStatus = modifyCharacterSavedStatus
                    )
                }
            }
        }
    }

    if (progressState) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Composable
@DevicePreviews
fun BookmarkScreenPreview() {
    MaterialTheme {
        BookmarkScreen(
            characterUiState = UiResult.Success(listOf(
                CharactersUiModel(
                    id = 1,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                    name = "hulk",
                    description = "description hello world",
                    urlCount = 1,
                    comicCount = 1,
                    storyCount = 1,
                    eventCount = 1,
                    seriesCount = 1,
                    saved = true,
                ),
                CharactersUiModel(
                    id = 2,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                    name = "hulk",
                    description = "description hello world",
                    urlCount = 1,
                    comicCount = 1,
                    storyCount = 1,
                    eventCount = 1,
                    seriesCount = 1,
                    saved = false,
                ),
                CharactersUiModel(
                    id = 3,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                    name = "hulk",
                    description = "description hello world",
                    urlCount = 1,
                    comicCount = 1,
                    storyCount = 1,
                    eventCount = 1,
                    seriesCount = 1,
                    saved = true,
                ),
                CharactersUiModel(
                    id = 4,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                    name = "hulk",
                    description = "description hello world",
                    urlCount = 1,
                    comicCount = 1,
                    storyCount = 1,
                    eventCount = 1,
                    seriesCount = 1,
                    saved = true,
                )
            )),
            progressState = false,
            onProgressStateChange = {},
            modifyCharacterSavedStatus = {_,_ ->}
        )
    }
}