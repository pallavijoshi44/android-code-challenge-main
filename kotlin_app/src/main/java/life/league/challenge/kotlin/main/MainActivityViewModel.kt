package life.league.challenge.kotlin.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.HttpLoginRepository

class MainActivityViewModel(
        private val repository: HttpLoginRepository,
        private val encodedCredentials: String
) : ViewModel() {

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.login(encodedCredentials)
            } catch (t: Throwable) {
                //TODO
            }
        }
    }


}