package life.league.challenge.kotlin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import life.league.challenge.kotlin.main.composable.LOADING_DIALOG
import life.league.challenge.kotlin.main.composable.LoadingDialog
import life.league.challenge.kotlin.main.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class LoadingDialogKtTest {

    @Rule
    @JvmField
    var composeTestRule = createComposeRule()

    @Test
    fun displayLoadingDialog() {
        with(composeTestRule) {
            setContent {
                AppTheme {
                   LoadingDialog()
                }
            }
            onNodeWithTag(LOADING_DIALOG).assertIsDisplayed()
        }
    }
}