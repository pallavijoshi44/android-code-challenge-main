package life.league.challenge.kotlin.main.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import life.league.challenge.kotlin.main.MainActivityViewModel
import life.league.challenge.kotlin.model.UserDetails

@Composable
fun MainScreen(uiState: MainActivityViewModel.UIState) {
    when (uiState) {
        is MainActivityViewModel.UIState.Data -> {
            val usersList = uiState.userDetails
            LazyColumn {
                items(usersList.size) { item ->
                    UserCard(user = usersList[item])
                }
            }
        }
        MainActivityViewModel.UIState.Loading -> LoadingDialog()
        is MainActivityViewModel.UIState.Error -> ErrorScreen(uiState.message)
    }
}

@Preview
@Composable
fun UserPreview() {
    UserCard(user = UserDetails("Belal", "username", "title", "null"))
}