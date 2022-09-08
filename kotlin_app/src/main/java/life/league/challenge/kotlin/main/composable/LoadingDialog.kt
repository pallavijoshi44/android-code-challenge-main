package life.league.challenge.kotlin.main.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

const val LOADING_DIALOG = "loading_dialog"

@Composable
fun LoadingDialog() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .testTag(LOADING_DIALOG),
        contentAlignment = Alignment.Center
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