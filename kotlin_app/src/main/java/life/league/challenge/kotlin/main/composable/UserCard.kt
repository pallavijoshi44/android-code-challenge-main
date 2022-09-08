package life.league.challenge.kotlin.main.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.model.UserDetails

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
                contentDescription = user.description,
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
                    .align(Alignment.CenterVertically)
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