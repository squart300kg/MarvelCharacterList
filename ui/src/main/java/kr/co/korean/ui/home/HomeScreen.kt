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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kr.co.korean.ui.model.CharactersUiModel
import kr.co.korean.work.ImageDownLoadResult
import kr.co.korean.ui.R as UiRes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSnackBarStateChanged: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val characterUiState = viewModel.characters.collectAsLazyPagingItems()
    val imageDownloadState by viewModel.imageDownloadState.collectAsStateWithLifecycle()
    var loadingProgressState by remember { mutableStateOf(false) }
    var refreshProgressState by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshProgressState,
        onRefresh = characterUiState::refresh
    )
    HomeScreen(
        modifier = modifier,
        refreshState = pullRefreshState,
        characterUiState = characterUiState,
        imageDownloadState = imageDownloadState,
        onSnackBarStateChanged = onSnackBarStateChanged,
        onRefreshProgressStateChange = { refreshProgressState = it },
        onLoadingProgressStateChange = { loadingProgressState = it },
        modifyCharacterSavedStatus = viewModel::modifyCharacterSavedStatus,
        downloadThumbnail = viewModel::downloadThumbnail
    )

    if (loadingProgressState) {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    refreshState: PullRefreshState,
    characterUiState: LazyPagingItems<CharactersUiModel>,
    imageDownloadState: ImageDownLoadResult,
    onSnackBarStateChanged: (String) -> Unit,
    onRefreshProgressStateChange: (Boolean) -> Unit,
    onLoadingProgressStateChange: (Boolean) -> Unit,
    modifyCharacterSavedStatus: (CharactersUiModel, Boolean) -> Unit,
    downloadThumbnail: (String) -> Unit
) {

    /**
     * 이미지 첫 로딩시 또, 새로고침시에 프로그래스바를 띄웁니다.
     * 또한 이미지 로딩이 완료(=[LoadState.NotLoading])시,
     * 이미지 다운을 위한 프로그래스바(=[ImageDownLoadResult]를 띄우게 됩니다.
     */
    when (characterUiState.loadState.refresh) {
        is LoadState.Loading -> {
            onLoadingProgressStateChange(true)
            onRefreshProgressStateChange(true)
        }
        is LoadState.Error -> {
            onLoadingProgressStateChange(false)
            onRefreshProgressStateChange(true)
        }
        is LoadState.NotLoading -> {
            onLoadingProgressStateChange(false)
            onRefreshProgressStateChange(false)

            when (imageDownloadState) {
                is ImageDownLoadResult.Loading -> {
                    onLoadingProgressStateChange(true)
                }
                is ImageDownLoadResult.Error -> {
                    onLoadingProgressStateChange(true)
                }
                is ImageDownLoadResult.Success -> {
                    onLoadingProgressStateChange(false)
                }
                is ImageDownLoadResult.NoneStart -> {
                    onLoadingProgressStateChange(false)
                }
            }

            if (characterUiState.loadState.append.endOfPaginationReached) {
                onSnackBarStateChanged(
                    stringResource(id = UiRes.string.pagingEndMessage)
                )
            }

            LazyColumn(modifier = modifier
                .pullRefresh(refreshState)
            ) {
                items(characterUiState.itemCount) { index ->
                    characterUiState[index]?.let { characterUiState ->
                        var imageProgressState by remember {
                            mutableStateOf(
                                true
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = UiRes.dimen.characterItemCommonPadding))
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(
                                        dimensionResource(id = UiRes.dimen.characterItemRoundCorner)
                                    )
                                )
                                .height(dimensionResource(id = UiRes.dimen.characterItemHeight))
                                .padding(dimensionResource(id = UiRes.dimen.characterItemCommonPadding))
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

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .fillMaxWidth(0.5f)
                                        .fillMaxHeight()
                                ) {
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
                                            .align(Alignment.Center)
                                            .clickable {
                                                downloadThumbnail(
                                                    characterUiState.thumbnail
                                                )
                                            },
                                        painter = rememberAsyncImagePainter(
                                            model = characterUiState.thumbnail,
                                            onState = { state ->
                                                imageProgressState =
                                                    when (state) {
                                                        is AsyncImagePainter.State.Loading -> true
                                                        is AsyncImagePainter.State.Empty,
                                                        is AsyncImagePainter.State.Error,
                                                        is AsyncImagePainter.State.Success -> false
                                                    }
                                            }),
                                        contentDescription = null,
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(
                                            start = dimensionResource(
                                                id = UiRes.dimen.characterItemCommonPadding
                                            )
                                        )
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = UiRes.string.characterItemUrlCount) + characterUiState.urlCount.toString()
                                    )
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = UiRes.string.characterItemComicCount) + characterUiState.comicCount.toString()
                                    )
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = UiRes.string.characterItemStoryCount) + characterUiState.storyCount.toString()
                                    )
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = UiRes.string.characterItemEventCount) + characterUiState.eventCount.toString()
                                    )
                                    Text(
                                        modifier = modifier,
                                        text = stringResource(id = UiRes.string.characterItemSeriesCount) + characterUiState.seriesCount.toString()
                                    )
                                }
                            }

                            Image(
                                modifier = Modifier
                                    .fillMaxWidth(0.2f)
                                    .fillMaxHeight(0.5f)
                                    .align(Alignment.CenterEnd),
                                painter = painterResource(id = characterUiState.bookMarkImage),
                                contentDescription = null
                            )
                        }

                    }
                }
            }
        }
    }

    Box(modifier = modifier
        .fillMaxWidth()) {
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.Center),
            refreshing = true,
            state = refreshState
        )
    }
}