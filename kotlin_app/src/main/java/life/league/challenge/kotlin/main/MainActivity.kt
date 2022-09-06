package life.league.challenge.kotlin.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.api.HttpLoginRepository
import life.league.challenge.kotlin.api.Service

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        // example api call to login, feel free to delete this and implement the call to login
        // somewhere else differently depending on your chosen architecture

        val httpLoginRepository = HttpLoginRepository(Service.api)
        val username = "hello"
        val password = "world"
        val credentials = android.util.Base64.encodeToString("$username:$password".toByteArray(), android.util.Base64.NO_WRAP) //TODO move this logic to viewmodel
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val account = httpLoginRepository.login(credentials)
                Log.v(TAG, account.apiKey ?: "")
            } catch (t : Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }

}
