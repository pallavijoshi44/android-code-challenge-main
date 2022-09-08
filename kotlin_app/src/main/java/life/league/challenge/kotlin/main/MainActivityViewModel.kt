package life.league.challenge.kotlin.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.HttpUsersRepository
import life.league.challenge.kotlin.model.UserDetails

class MainActivityViewModel(
    private val repository: HttpUsersRepository,
    private val encodedCredentials: String
) : ViewModel() {

    private val _uiState : MutableStateFlow<UIState> = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> get() = _uiState

    init {
        viewModelScope.launch {
            try {
                _uiState.value = UIState.Data(repository.fetchUserDetails(encodedCredentials))
            } catch (e: Exception) {

            }
        }
    }

    sealed class UIState {
        object Loading: UIState()
        data class Data(val userDetails: List<UserDetails>) : UIState()
    }
}