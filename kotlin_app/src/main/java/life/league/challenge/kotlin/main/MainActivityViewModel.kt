package life.league.challenge.kotlin.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import life.league.challenge.kotlin.api.HttpLoginRepository
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(
        private val repository: HttpLoginRepository,
        private val encodedCredentials: String,
        private val coroutineContext: CoroutineContext = Dispatchers.IO) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.LoggedOut)
    val uiState: LiveData<UiState>
        get() = _uiState

    private val coroutineScope = viewModelScope + coroutineContext

    init {
        coroutineScope.launch {
            try {
                repository.login(encodedCredentials)
                _uiState.value = UiState.LoggedIn
            } catch (t: Throwable) {
                //   _uiState.value = UiState.Error
            }
        }
    }

    sealed class UiState {
        object LoggedIn : UiState()
        object LoggedOut : UiState()
        object Error : UiState()
    }
}