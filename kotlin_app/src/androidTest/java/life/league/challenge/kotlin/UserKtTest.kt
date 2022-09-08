package life.league.challenge.kotlin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import life.league.challenge.kotlin.main.composable.UserCard
import life.league.challenge.kotlin.main.theme.AppTheme
import life.league.challenge.kotlin.model.UserDetails
import org.junit.Rule
import org.junit.Test

class UserCardKtTest {

    @Rule
    @JvmField
    var composeTestRule = createComposeRule()

    @Test
    fun displayUserPostsCorrectly() {
        with(composeTestRule) {
            setContent {
                AppTheme {
                    UserCard(UserDetails("avatar", "Charlie", "title1", "description1"))
                }
            }
            onNodeWithContentDescription("description1").assertIsDisplayed()
            onNodeWithText("Charlie").assertIsDisplayed()
            onNodeWithText("title1").assertIsDisplayed()
            onNodeWithText("description1").assertIsDisplayed()
        }
    }
}