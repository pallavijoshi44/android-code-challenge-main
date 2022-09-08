package life.league.challenge.kotlin.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.HttpUsersRepository
import life.league.challenge.kotlin.api.NoPostsFoundException
import life.league.challenge.kotlin.model.UserDetails

class MainActivityViewModel(
    private val repository: HttpUsersRepository,
    private val encodedCredentials: String
) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> get() = _uiState

    init {
        viewModelScope.launch {
            when (val result = repository.fetchUserDetails(encodedCredentials)) {
                is Either.Left -> _uiState.value = if (result.value is NoPostsFoundException) {
                    UIState.Error(NO_POST_FOUND_TEXT_MESSAGE)
                } else {
                    UIState.Error(ERROR_TEXT_MESSAGE)
                }
                is Either.Right -> _uiState.value = UIState.Data(result.value)
            }
        }
    }

    sealed class UIState {
        object Loading : UIState()
        data class Data(val userDetails: List<UserDetails>) : UIState()
        data class Error(val message: String) : UIState()
    }

    companion object {
        const val ERROR_TEXT_MESSAGE = "An Error occurred! Please try again later."
        const val NO_POST_FOUND_TEXT_MESSAGE = "No posts are found!"
    }
}