package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.appcompattheme.AppCompatTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel {
        val credentials = android.util.Base64.encodeToString(
            "$USERNAME:$PASSWORD".toByteArray(),
            android.util.Base64.NO_WRAP
        )
        parametersOf(credentials)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val usersList = viewModel.usersPager.collectAsLazyPagingItems()
            AppCompatTheme {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen() {
        Text(
            text = "ABC",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }

    companion object {
        const val USERNAME = "hello"
        const val PASSWORD = "world"
    }
}
