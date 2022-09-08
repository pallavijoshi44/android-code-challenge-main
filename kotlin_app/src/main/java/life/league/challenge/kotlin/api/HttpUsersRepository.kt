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

    private var userList: List<User>? = null

    suspend fun fetchUserDetails(credentials: String, id: Int): List<UserDetails> {
        val users = fetchUsers(credentials)
        val posts = fetchPosts(credentials, id)
        val user: User? = users.find { user -> user.id == id }
        val userDetails: List<UserDetails> = posts.map { post ->
            UserDetails(
                user?.avatar,
                user?.username ?: "",
                post.title,
                post.description
            )
        }
        return userDetails
    }

    private suspend fun fetchUsers(credentials: String): List<User> {
        if (userList == null) {
            val userInfo = withContext(Dispatchers.IO) {
                val account = httpLoginRepository.login(credentials)
                api.fetchUsers(account.apiKey)
            }
            userList = userInfo
        }
        return userList ?: emptyList()
    }

    private suspend fun fetchPosts(credentials: String, id: Int): List<Post> {
        val posts = withContext(Dispatchers.IO) {
            val account = httpLoginRepository.login(credentials)
            api.fetchUserPosts(account.apiKey, id)
        }
        return posts
    }

}