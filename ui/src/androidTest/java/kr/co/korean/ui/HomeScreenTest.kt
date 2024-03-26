package kr.co.korean.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
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

    @Test
    fun progressBar_whenAppFirstLoaded_showCharacters() {
        composeTestRule.setContent {
            BoxWithConstraints {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    refreshState = rememberPullRefreshState(
                        refreshing = false,
                        onRefresh = {}
                    ),
                    characterUiState = flowOf(
                        PagingData.from(
                            data = characterUiModelsTestData,
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
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

        repeat(characterUiModelsTestData.size) { index ->
            composeTestRule
                .onNode(hasScrollToNodeAction())
                .performScrollToNode(
                    hasText(characterUiModelsTestData[index].name)
                )

            composeTestRule
                .onNodeWithText(
                    characterUiModelsTestData[index].name
                )
                .assertExists()
        }

//        composeTestRule
//            .onNodeWithText(
//                characterUiModelsTestData[0].name
//            )
//            .assertExists()
//
//        composeTestRule
//            .onNode(hasScrollToNodeAction())
//            .performScrollToNode(
//                hasText(characterUiModelsTestData[1].name)
//            )
//
//        composeTestRule
//            .onNodeWithText(
//                characterUiModelsTestData[1].name
//            )
//            .assertExists()
//
//        composeTestRule
//            .onNode(hasScrollToNodeAction())
//            .performScrollToNode(
//                hasText(characterUiModelsTestData[2].name)
//            )
//
//        composeTestRule
//            .onNodeWithText(
//                characterUiModelsTestData[2].name
//            )
//            .assertExists()
//
//        composeTestRule
//            .onNode(hasScrollToNodeAction())
//            .performScrollToNode(
//                hasText(characterUiModelsTestData[3].name)
//            )
//
//        composeTestRule
//            .onNodeWithText(
//                characterUiModelsTestData[3].name
//            )
//            .assertExists()


    }
}