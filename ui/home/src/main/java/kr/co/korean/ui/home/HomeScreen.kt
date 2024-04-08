package kr.co.korean.ui.home

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import kr.co.korean.home.R
import kr.co.korean.ui.base.BaseCharacterItem
import kr.co.korean.model.CharactersUiModel
import kr.co.korean.ui.base.isDetailPaneVisible
import kr.co.korean.ui.detail.DETAIL_PLACEHOLDER_BASE_ROUTE
import kr.co.korean.ui.detail.DetailPlaceHolderScreen
import kr.co.korean.ui.detail.DetailScreen
import kr.co.korean.ui.detail.navigation.DETAIL_ROUTE
import kr.co.korean.ui.detail.navigation.DETAIL_ROUTE_BASE
import kr.co.korean.ui.detail.navigation.navigateToDetailScreen
import kr.co.korean.util.CharacterUiModelPreviewParameterProvider
import kr.co.korean.util.DevicePreviews
import kr.co.korean.work.ImageDownLoadResult

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
        loadingProgressState = loadingProgressState,
        onLoadingProgressStateChange = { loadingProgressState = it },
        onRefreshProgressStateChange = { refreshProgressState = it },
        onSnackBarStateChanged = onSnackBarStateChanged,
        onModifyCharacterSavedStatus = viewModel::modifyCharacterSavedStatus,
        onDownloadThumbnail = viewModel::downloadThumbnail,
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    refreshState: PullRefreshState,
    characterUiState: LazyPagingItems<CharactersUiModel>,
    imageDownloadState: ImageDownLoadResult,
    loadingProgressState: Boolean,
    onLoadingProgressStateChange: (Boolean) -> Unit,
    onSnackBarStateChanged: (String) -> Unit,
    onRefreshProgressStateChange: (Boolean) -> Unit,
    onModifyCharacterSavedStatus: (CharactersUiModel, Boolean) -> Unit,
    onDownloadThumbnail: (String) -> Unit,
    context: Context = LocalContext.current,
) {

    /**
     * 이미지 첫 로딩시 또, 새로고침시에 프로그래스바를 띄웁니다.
     * 또한 이미지 로딩이 완료(=[LoadState.NotLoading])시,
     * 이미지 다운을 위한 프로그래스바(=[ImageDownLoadResult]를 띄우게 됩니다.
     */
    when (val loadState = characterUiState.loadState.refresh) {
        is LoadState.Loading -> {
            LaunchedEffect(Unit) {
                onLoadingProgressStateChange(true)
                onRefreshProgressStateChange(true)
            }
        }
        is LoadState.Error -> {
            LaunchedEffect(Unit) {
                onLoadingProgressStateChange(false)
                onRefreshProgressStateChange(false)
                onSnackBarStateChanged(loadState.error.message ?: "")
            }
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
                    LaunchedEffect(Unit) {
                        onSnackBarStateChanged(context.getString(R.string.savedError))
                    }
                }
                is ImageDownLoadResult.Success -> {
                    LaunchedEffect(Unit) {
                        onSnackBarStateChanged(context.getString(R.string.savedSuccess))
                    }
                }
            }

            if (characterUiState.loadState.append.endOfPaginationReached) {
                onSnackBarStateChanged(
                    stringResource(id = R.string.pagingEndMessage)
                )
            }

            val nestedNavController = rememberNavController()
            val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
            BackHandler(listDetailNavigator.canNavigateBack()) {
                listDetailNavigator.navigateBack()
            }

            ListDetailPaneScaffold(
                directive = listDetailNavigator.scaffoldDirective,
                value = listDetailNavigator.scaffoldValue,
                listPane = {
                    LazyColumn(modifier = Modifier
                        .pullRefresh(refreshState)
                    ) {
                        items(characterUiState.itemCount) { index ->
                            characterUiState[index]?.let { characterUiState ->
                                BaseCharacterItem(
                                    characterUiState = characterUiState,
                                    onModifyingCharacterSavedStatus = onModifyCharacterSavedStatus,
                                    onDownloadThumbnail = onDownloadThumbnail,
                                    highlightSelectedItem = listDetailNavigator.isDetailPaneVisible(),
                                    onNavigateToCharacterDetail = { id ->
                                        nestedNavController.navigateToDetailScreen(id) {
                                            popUpTo(DETAIL_PLACEHOLDER_BASE_ROUTE)
                                        }
                                        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                    }
                                )
                            }
                        }
                    }
                },
                detailPane = {
                    NavHost(
                        navController = nestedNavController,
                        startDestination = DETAIL_ROUTE_BASE,
                        route = DETAIL_PLACEHOLDER_BASE_ROUTE
                    ) {
                        composable(route = DETAIL_ROUTE) {
                            DetailScreen(
                                onBackClick = listDetailNavigator::navigateBack
                            )
                        }
                        composable(route = DETAIL_ROUTE_BASE) {
                            DetailPlaceHolderScreen()
                        }
                    }
                }
            )
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

    if (loadingProgressState) {
        Box(modifier = modifier
            .fillMaxSize()
            .semantics { contentDescription = context.getString(R.string.semanticProgressBar) }
        ) {
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
@DevicePreviews
@Composable
fun HomeScreenPreview(
    @PreviewParameter(CharacterUiModelPreviewParameterProvider::class)
    characterUiModels: List<CharactersUiModel>
) {
    MaterialTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            refreshState = rememberPullRefreshState(
                refreshing = true,
                onRefresh = {}
            ),
            characterUiState = flowOf(PagingData.from(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                ),
                data = characterUiModels
            )).collectAsLazyPagingItems(),
            imageDownloadState = ImageDownLoadResult.Loading,
            onSnackBarStateChanged = { state -> },
            onRefreshProgressStateChange = { state -> },
            onLoadingProgressStateChange = { state -> },
            onModifyCharacterSavedStatus = { uiModel, saved -> },
            onDownloadThumbnail = { url -> },
            loadingProgressState = false
        )
    }

}