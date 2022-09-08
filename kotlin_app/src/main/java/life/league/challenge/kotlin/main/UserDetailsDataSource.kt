package life.league.challenge.kotlin.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import life.league.challenge.kotlin.api.HttpUsersRepository
import life.league.challenge.kotlin.model.UserDetails

class UserDetailsDataSource(
    private val repo: HttpUsersRepository,
    private val credentials: String,
) : PagingSource<Int, UserDetails>() {

    override fun getRefreshKey(state: PagingState<Int, UserDetails>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDetails> {
        return try {
            val page = params.key ?: 1
            val userDetails = repo.fetchUserDetails(credentials, page)
            LoadResult.Page(
                data = userDetails,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}