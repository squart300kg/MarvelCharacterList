package kr.co.korean.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kr.co.korean.ui.R
import kr.co.korean.ui.base.BaseCharacterItem
import kr.co.korean.model.CharactersUiModel
import kr.co.korean.work.ImageDownLoadResult
import kr.co.korean.ui.R as UiRes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSnackBarStateChanged: (String) -> Unit,
    onNavigateToCharacterDetail: () -> Unit,
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
        onModifyCharacterSavedStatus = viewModel::modifyCharacterSavedStatus,
        onDownloadThumbnail = viewModel::downloadThumbnail
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
    onModifyCharacterSavedStatus: (CharactersUiModel, Boolean) -> Unit,
    onDownloadThumbnail: (String) -> Unit
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
            onRefreshProgressStateChange(false)

            when (imageDownloadState) {
                is ImageDownLoadResult.Loading -> {
                    onLoadingProgressStateChange(true)
                }
                is ImageDownLoadResult.NoneStart -> {
                    onLoadingProgressStateChange(false)
                }
                is ImageDownLoadResult.Error -> {
                    onSnackBarStateChanged(stringResource(id = UiRes.string.savedError))
                }
                is ImageDownLoadResult.Success -> {
                    onSnackBarStateChanged(stringResource(id = UiRes.string.savedSuccess))
                }
            }

            if (characterUiState.loadState.append.endOfPaginationReached) {
                onSnackBarStateChanged(
                    stringResource(id = UiRes.string.pagingEndMessage)
                )
            }

            var searchText by remember { mutableStateOf("") }
            Column(modifier = Modifier) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.characterItemCommonPadding))
                        .padding(top = dimensionResource(id = R.dimen.characterItemCommonPadding))
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(
                                dimensionResource(id = R.dimen.searchTextFieldRadius)
                            )
                        )
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.searchIconStartMargin))
                            .size(dimensionResource(id = R.dimen.searchReadingGlassesSize))
                            .align(Alignment.CenterStart),
                        painter = painterResource(id = R.drawable.ic_reading_glasses),
                        contentDescription = null
                    )

                    TextField(
                        modifier = Modifier
                            .align(Alignment.Center),
                        textStyle = TextStyle(color = Color.White),
                        value = searchText,
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.searchPlaceHolder),
                                style = TextStyle(color = Color.Gray)
                            )
                        },
                        onValueChange = { searchText = it }
                    )

                    if (searchText.isNotEmpty()) {
                        Image(
                            modifier = Modifier
                                .padding(end = dimensionResource(id = R.dimen.searchTextFieldHorizontalMargin))
                                .align(Alignment.CenterEnd)
                                .clickable { searchText = "" },
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = null)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.marginBetweenSearchIconAndTextField))

                )

                LazyColumn(modifier = Modifier
                    .pullRefresh(refreshState)
                ) {
                    items(characterUiState.itemCount) { index ->
                        characterUiState[index]?.let { characterUiState ->
                            BaseCharacterItem(
                                characterUiState = characterUiState,
                                onModifyingCharacterSavedStatus = onModifyCharacterSavedStatus,
                                onDownloadThumbnail = onDownloadThumbnail
                            )
                        }
                    }
                }
            }
        }
    }

    Box(modifier = modifier
        .fillMaxWidth()
    ) {
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.Center),
            refreshing = true,
            state = refreshState
        )
    }
}