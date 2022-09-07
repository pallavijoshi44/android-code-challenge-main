package life.league.challenge.kotlin.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.HttpLoginRepository

class MainActivityViewModel(
        private val repository: HttpLoginRepository,
        private val encodedCredentials: String
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.LoggedOut)
    val uiState: LiveData<UiState>
        get() = _uiState

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.login(encodedCredentials)
            } catch (t: Throwable) {
                //TODO
            }
        }
    }

    sealed class UiState {
        object LoggedIn : UiState()
        object LoggedOut : UiState()
    }
}