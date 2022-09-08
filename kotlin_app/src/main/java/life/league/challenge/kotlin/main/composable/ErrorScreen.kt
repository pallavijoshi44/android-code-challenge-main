package life.league.challenge.kotlin.main.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val ERROR_TEXT_MESSAGE = "An Error occurred! Please try again later."

@Composable
fun ErrorScreen() {
    Text(
        color = Color.White,
        text = ERROR_TEXT_MESSAGE,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 12.dp)
    )
}
