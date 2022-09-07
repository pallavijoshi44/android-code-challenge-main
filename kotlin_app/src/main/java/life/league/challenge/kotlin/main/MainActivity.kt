package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.google.android.material.composethemeadapter.MdcTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.HttpLoginRepository
import life.league.challenge.kotlin.api.Service

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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

    override fun onResume() {
        super.onResume()

        //TODO remove this logic after viewmodel is used
        val httpLoginRepository = HttpLoginRepository(Service.api)
        val username = "hello"
        val password = "world"
        val credentials = android.util.Base64.encodeToString("$username:$password".toByteArray(), android.util.Base64.NO_WRAP) //TODO move this logic to viewmodel
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                httpLoginRepository.login(credentials)
            } catch (t: Throwable) {
            }
        }
    }

}
