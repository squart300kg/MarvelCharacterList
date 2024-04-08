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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.korean.common.model.UiResult
import kr.co.korean.ui.base.BaseCharacterItem
import kr.co.korean.model.CharactersUiModel
import kr.co.korean.util.CharacterUiModelPreviewParameterProvider
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
fun BookmarkScreenPreview(
    @PreviewParameter(CharacterUiModelPreviewParameterProvider::class)
    characterUiModels: List<CharactersUiModel>
) {
    MaterialTheme {
        BookmarkScreen(
            characterUiState = UiResult.Success(characterUiModels),
            progressState = false,
            onProgressStateChange = { },
            modifyCharacterSavedStatus = { _,_ ->}
        )
    }
}