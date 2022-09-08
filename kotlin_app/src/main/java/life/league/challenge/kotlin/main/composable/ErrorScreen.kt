package life.league.challenge.kotlin.main.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ErrorScreen(message: String) {
    Text(
        color = Color.Black,
        text = message,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(20.dp)
    )
}
