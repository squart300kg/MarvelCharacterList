package kr.co.korean.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import kr.co.korean.model.CharactersUiModel
import kr.co.korean.ui.home.HomeScreen
import kr.co.korean.ui.model.characterUiModelsTestData
import kr.co.korean.work.ImageDownLoadResult
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterialApi::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun progressBar_whenAppFirstLoading_showProgressBar() {

        composeTestRule.setContent {
            BoxWithConstraints {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    refreshState = rememberPullRefreshState(
                        refreshing = false,
                        onRefresh = {}
                    ),
                    characterUiState = flowOf(
                        PagingData.empty<CharactersUiModel>(
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.Loading,
                                append = LoadState.Loading,
                                prepend = LoadState.Loading,
                            )
                        )
                    ).collectAsLazyPagingItems(),
                    imageDownloadState = ImageDownLoadResult.NoneStart,
                    loadingProgressState = true,
                    onLoadingProgressStateChange = { state -> },
                    onRefreshProgressStateChange = { state -> },
                    onSnackBarStateChanged = { state -> },
                    onModifyCharacterSavedStatus = { uiModel, saved -> },
                    onDownloadThumbnail = { url -> }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(
                label = composeTestRule.activity.resources.getString(R.string.semanticProgressBar)
            )
            .assertExists()
    }
}