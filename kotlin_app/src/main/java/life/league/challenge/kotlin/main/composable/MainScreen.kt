package life.league.challenge.kotlin.main.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.league.challenge.kotlin.R
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
        MainActivityViewModel.UIState.Error -> TODO()
    }

}

@Composable
fun ErrorItem(message: String) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp),
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}


@Preview
@Composable
fun UserPreview() {
    UserCard(user = UserDetails("Belal", "username", "title", "null"))
}