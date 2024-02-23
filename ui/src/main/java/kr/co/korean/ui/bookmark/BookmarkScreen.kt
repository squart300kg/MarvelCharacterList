package kr.co.korean.ui.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kr.co.korean.common.model.Result
//import kr.co.korean.investment.ui.theme.KoreanInvestmentTheme
//import kr.co.korean.investment.ui.theme.Pink80
//import kr.co.korean.investment.ui.theme.Purple80
//import kr.co.korean.investment.ui.theme.PurpleGrey80
import kr.co.korean.ui.R
import kr.co.korean.ui.base.BaseCharacterItem
import kr.co.korean.ui.model.CharactersUiModel
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
    characterUiState: Result<List<CharactersUiModel>>,
    progressState: Boolean,
    onProgressStateChange: (Boolean) -> Unit,
    modifyCharacterSavedStatus: (CharactersUiModel, Boolean) -> Unit
) {
    when (characterUiState) {
        is Result.Loading -> onProgressStateChange(true)
        is Result.Error -> onProgressStateChange(false)
        is Result.Success -> {
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
            characterUiState = Result.Success(listOf(
                CharactersUiModel(
                    id = 1,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                    urlCount = 1,
                    comicCount = 1,
                    storyCount = 1,
                    eventCount = 1,
                    seriesCount = 1,
                    saved = true,
                ),
                CharactersUiModel(
                    id = 1,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                    urlCount = 1,
                    comicCount = 1,
                    storyCount = 1,
                    eventCount = 1,
                    seriesCount = 1,
                    saved = false,
                ),
                CharactersUiModel(
                    id = 1,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                    urlCount = 1,
                    comicCount = 1,
                    storyCount = 1,
                    eventCount = 1,
                    seriesCount = 1,
                    saved = true,
                ),
                CharactersUiModel(
                    id = 1,
                    thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
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