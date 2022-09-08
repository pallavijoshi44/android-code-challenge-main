package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import life.league.challenge.kotlin.main.composable.MainScreen
import life.league.challenge.kotlin.main.theme.AppTheme
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
            val uiState: MainActivityViewModel.UIState by viewModel.uiState.collectAsState()

            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(uiState)
                }
            }
        }
    }

    companion object {
        const val USERNAME = "hello"
        const val PASSWORD = "world"
    }
}
