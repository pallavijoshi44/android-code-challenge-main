package life.league.challenge.kotlin.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.HttpUsersRepository

class MainActivityViewModel(
        private val repository: HttpUsersRepository,
        private val encodedCredentials: String,
        appDispatchers: AppDispatchers = AppDispatchers()) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.LoggedOut)
    val uiState: LiveData<UiState>
        get() = _uiState

    // private val coroutineScope = viewModelScope + coroutineContext.IO

    init {
        viewModelScope.launch(appDispatchers.IO) {
            try {
                val x = repository.fetchUserDetails(encodedCredentials)
                _uiState.value = UiState.LoggedIn
            } catch (t: Throwable) {
                t.printStackTrace()
                _uiState.value = UiState.Error
            }
        }
    }

    sealed class UiState {
        object LoggedIn : UiState()
        object LoggedOut : UiState()
        object Error : UiState()
    }
}