package life.league.challenge.kotlin.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.UserDetails

class HttpUsersRepository(
    private val httpLoginRepository: HttpLoginRepository,
    private val api: Api
) {

    suspend fun fetchUserDetails(credentials: String): List<UserDetails> {
        val users = fetchUsers(credentials)
        val posts = fetchPosts(credentials)
        val userDetails: List<UserDetails> = posts.map { post ->
            val user: User? = users.find { user -> user.id == post.userId }
            UserDetails(
                user?.avatar,
                user?.name ?: "",
                post.title,
                post.description
            )
        }
        return userDetails
    }

    private suspend fun fetchUsers(credentials: String): List<User> =
        withContext(Dispatchers.IO) {
            val account = httpLoginRepository.login(credentials)
            api.fetchUsers(account.apiKey)
        }

    private suspend fun fetchPosts(credentials: String): List<Post> =
        withContext(Dispatchers.IO) {
            val account = httpLoginRepository.login(credentials)
            api.fetchUserPosts(account.apiKey)
        }
}