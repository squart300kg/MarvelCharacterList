package kr.co.korean.ui.home

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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kr.co.korean.ui.R

// TODO:
//  1. 프리뷰 작업
//  2. 디바이스 크기게 맞게 컴포넌트 조절되도록 설정

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val characterUiState = viewModel.characters.collectAsLazyPagingItems()
    var progressState by remember { mutableStateOf(true) }
    HomeScreen(
        modifier = modifier,
        characterUiState = characterUiState,
        progressState = progressState,
        onProgressStateChange = { progressState = it},
        modifyCharacterSavedStatus = viewModel::modifyCharacterSavedStatus
    )

}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    characterUiState: LazyPagingItems<CharactersUiModel>,
    progressState: Boolean,
    onProgressStateChange: (Boolean) -> Unit,
    modifyCharacterSavedStatus: (CharactersUiModel, Boolean) -> Unit
) {
    when (characterUiState.loadState.refresh) {
        is LoadState.Loading -> onProgressStateChange(true)
        is LoadState.Error -> onProgressStateChange(false)
        is LoadState.NotLoading -> {
            onProgressStateChange(false)

            LazyColumn(modifier = modifier) {
                items(characterUiState.itemCount) { index  ->
                    characterUiState[index]?.let { characterUiState ->
                        var imageProgressState by remember { mutableStateOf(true) }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.characterItemCommonPadding))
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(
                                        dimensionResource(id = R.dimen.characterItemRoundCorner)
                                    )
                                )
                                .height(dimensionResource(id = R.dimen.characterItemHeight))
                                .padding(dimensionResource(id = R.dimen.characterItemCommonPadding))
                                .clickable {
                                    modifyCharacterSavedStatus(
                                        characterUiState,
                                        !characterUiState.saved
                                    )
                                }

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .align(Alignment.CenterStart),
                            ) {

                                Box(modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .fillMaxWidth(0.5f)
                                    .fillMaxHeight()) {
                                    if (imageProgressState) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .align(Alignment.Center),
                                            color = MaterialTheme.colorScheme.tertiary,
                                        )
                                    }
                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .align(Alignment.Center),
                                        painter = rememberAsyncImagePainter(
                                            model = characterUiState.thumbnail,
                                            onState = { state ->
                                                imageProgressState = when (state) {
                                                    is AsyncImagePainter.State.Loading -> true
                                                    is AsyncImagePainter.State.Empty,
                                                    is AsyncImagePainter.State.Error,
                                                    is AsyncImagePainter.State.Success -> false
                                                }
                                            }),
                                        contentDescription = null
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(
                                            start = dimensionResource(
                                                id = R.dimen.characterItemCommonPadding
                                            )
                                        )
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // TODO: uiState바꾸면서 중복 제거할 것
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = R.string.characterItemUrlCount) + characterUiState.urlCount.toString())
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = R.string.characterItemComicCount) + characterUiState.comicCount.toString())
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = R.string.characterItemStoryCount) + characterUiState.storyCount.toString())
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = R.string.characterItemEventCount) + characterUiState.eventCount.toString())
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = R.string.characterItemSeriesCount) + characterUiState.seriesCount.toString())
                                }
                            }

                            Image(
                                modifier = Modifier
                                    .fillMaxWidth(0.2f)
                                    .fillMaxHeight(0.5f)
                                    .align(Alignment.CenterEnd),
                                painter = painterResource(id = characterUiState.bookMarkImage),
                                contentDescription = null)
                        }

                    }
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