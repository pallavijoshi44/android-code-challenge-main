package life.league.challenge.kotlin.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.UserDetails

class HttpUsersRepository(
        private val httpLoginRepository: HttpLoginRepository,
        private val api: Api) {

    suspend fun fetchUserDetails(credentials: String): List<UserDetails> {
        val userDetails = mutableListOf<UserDetails>()
        val users = fetchUsers(credentials)
        users.forEach { user ->
            val posts = fetchPosts(credentials, user.id)
            posts.forEach { post ->
                userDetails.add(UserDetails(user.avatar, user.username, post.title, post.description))
            }
        }
        return userDetails
    }

    private suspend fun fetchUsers(credentials: String): List<User> {
        val userInfo = withContext(Dispatchers.IO) {
            val account = httpLoginRepository.login(credentials)
            api.fetchUsers(account.apiKey)
        }
        return userInfo
    }

    private suspend fun fetchPosts(credentials: String, id: Int): List<Post> {
        val posts = withContext(Dispatchers.IO) {
            val account = httpLoginRepository.login(credentials)
            api.fetchUserPosts(account.apiKey, id)
        }
        return posts
    }

}