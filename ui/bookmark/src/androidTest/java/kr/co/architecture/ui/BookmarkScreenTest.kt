package kr.co.architecture.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kr.co.architecture.common.model.UiResult
import kr.co.architecture.ui.model.characterUiModelsTestData
import kr.co.architecture.ui.bookmark.BookmarkScreen

import org.junit.Test

import org.junit.Rule

class BookmarkScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun useAppContext() {
        composeTestRule.setContent {
            BookmarkScreen(
                characterUiState = UiResult.Success(characterUiModelsTestData),
                progressState = true,
                onProgressStateChange = {},
                modifyCharacterSavedStatus = { _, _ -> }
            )
        }


    }
}