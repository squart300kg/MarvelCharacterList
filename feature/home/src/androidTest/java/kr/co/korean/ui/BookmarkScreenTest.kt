package kr.co.korean.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kr.co.korean.common.model.UiResult
import kr.co.korean.ui.model.characterUiModelsTestData
import kr.co.korean.ui.bookmark.BookmarkScreen

import org.junit.Test

import org.junit.Rule

class BookmarkScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun useAppContext() {
        composeTestRule.setContent {
            BoxWithConstraints {
                BookmarkScreen(
                    characterUiState = UiResult.Success(characterUiModelsTestData),
                    progressState = true,
                    onProgressStateChange = {},
                    modifyCharacterSavedStatus = { _, _ -> }
                )
            }
        }


    }
}