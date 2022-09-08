package life.league.challenge.kotlin.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import life.league.challenge.kotlin.api.HttpUsersRepository

class MainActivityViewModel(
    private val repository: HttpUsersRepository,
    private val encodedCredentials: String
) : ViewModel() {

   // private val coroutineScope = viewModelScope + coroutineContext.IO

    val usersPager = Pager(
        PagingConfig(pageSize = 10)
    ) {
        UserDetailsDataSource(repository, encodedCredentials)
    }.flow.cachedIn(viewModelScope)

}