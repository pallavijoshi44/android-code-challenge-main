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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            } catch (t : Throwable) {
            }
        }
    }

}
