package life.league.challenge.kotlin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import life.league.challenge.kotlin.main.MainActivityViewModel
import life.league.challenge.kotlin.main.composable.ERROR_TEXT_MESSAGE
import life.league.challenge.kotlin.main.composable.LOADING_DIALOG
import life.league.challenge.kotlin.main.composable.MainScreen
import life.league.challenge.kotlin.main.theme.AppTheme
import life.league.challenge.kotlin.model.UserDetails
import org.junit.Rule
import org.junit.Test

class MainScreenKtTest {

    @Rule
    @JvmField
    var composeTestRule = createComposeRule()

    @Test
    fun displayUserPosts_WhenUIStateIsData() {
        with(composeTestRule) {
            setContent {
                AppTheme {
                    val userDetails =
                        listOf(
                            UserDetails("avatar", "Charlie", "title1", "description1"),
                            UserDetails("avatar", "Tom", "title2", "description2")
                        )
                    MainScreen(uiState = MainActivityViewModel.UIState.Data(userDetails))
                }
            }
            onNodeWithContentDescription("description1").assertIsDisplayed()
            onNodeWithContentDescription("description2").assertIsDisplayed()
        }
    }

    @Test
    fun displayLoadingIndicator_WhenUIStateIsLoading() {
        with(composeTestRule) {
            setContent {
                AppTheme {
                    MainScreen(uiState = MainActivityViewModel.UIState.Loading)
                }
            }
            onNodeWithTag(LOADING_DIALOG).assertIsDisplayed()
        }
    }

    @Test
    fun displayErrorScreen_WhenUIStateIsError() {
        with(composeTestRule) {
            setContent {
                AppTheme {
                    MainScreen(uiState = MainActivityViewModel.UIState.Error)
                }
            }
            onNodeWithText(ERROR_TEXT_MESSAGE).assertIsDisplayed()
        }
    }
}