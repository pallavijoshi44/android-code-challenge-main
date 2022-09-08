package life.league.challenge.kotlin.main.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.main.MainActivityViewModel
import life.league.challenge.kotlin.model.UserDetails


@Composable
fun UserList(viewModel: MainActivityViewModel) {

    val uiState : MainActivityViewModel.UIState by viewModel.uiState.collectAsState()

    when(uiState){
        is MainActivityViewModel.UIState.Data -> {
            val usersList = (uiState as MainActivityViewModel.UIState.Data).userDetails
            LazyColumn {
                items(usersList.size) { item ->
                    UserCard(user = usersList[item]) }
            }
        }
        MainActivityViewModel.UIState.Loading -> TODO()
        MainActivityViewModel.UIState.Error -> TODO()
    }

}


@Composable
fun UserCard(user: UserDetails) {
    Column(
        modifier = Modifier
            .padding(top = 12.dp, start = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatar)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_avatar_placeholder),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(48.dp)
                    .height(48.dp)
            )
            Text(
                text = user.userName,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
        Text(
            text = user.title,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 12.dp, bottom = 5.dp, top = 5.dp)
        )
        user.description?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        }
        Divider(Modifier.padding(top = 12.dp, start = 12.dp))
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .padding(8.dp),
            strokeWidth = 5.dp
        )

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