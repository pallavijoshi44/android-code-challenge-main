package life.league.challenge.kotlin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import life.league.challenge.kotlin.main.composable.ERROR_TEXT_MESSAGE
import life.league.challenge.kotlin.main.composable.ErrorScreen
import life.league.challenge.kotlin.main.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class ErrorScreenKtTest {

    @Rule
    @JvmField
    var composeTestRule = createComposeRule()

    @Test
    fun displayErrorScreen() {
        with(composeTestRule) {
            setContent {
                AppTheme {
                   ErrorScreen()
                }
            }
            onNodeWithText(ERROR_TEXT_MESSAGE).assertIsDisplayed()
        }
    }
}