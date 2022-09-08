package life.league.challenge.kotlin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import life.league.challenge.kotlin.main.MainActivityViewModel
import life.league.challenge.kotlin.main.composable.UserList
import life.league.challenge.kotlin.main.theme.AppTheme
import life.league.challenge.kotlin.model.UserDetails
import org.junit.Rule
import org.junit.Test

class UserListKtTest {

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
                                UserDetails("avatar", "Tom", "title2", "description2"))
                        UserList(uiState = MainActivityViewModel.UIState.Data(userDetails))
                    }
                }
                onNodeWithContentDescription("description1").assertIsDisplayed()
                onNodeWithText("Charlie").assertIsDisplayed()
                onNodeWithText("title1").assertIsDisplayed()
                onNodeWithText("description1").assertIsDisplayed()

                onNodeWithContentDescription("description2").assertIsDisplayed()
                onNodeWithText("Tom").assertIsDisplayed()
                onNodeWithText("title2").assertIsDisplayed()
                onNodeWithText("description2").assertIsDisplayed()
            }
    }
}