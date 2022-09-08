package life.league.challenge.kotlin.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import life.league.challenge.kotlin.model.User

class HttpUsersRepository(private val httpLoginRepository: HttpLoginRepository, private val api: Api) {

    suspend fun fetchUsers(credentials: String): User {
        withContext(Dispatchers.IO) {
            val account = httpLoginRepository.login(credentials)
            val userInfo = api.fetchUsers(account.apiKey)
        }
        return User()
    }

    suspend fun fetchPosts(credentials: String): Unit {
        withContext(Dispatchers.IO) {
            val account = httpLoginRepository.login(credentials)
            val posts = api.fetchUserPosts(account.apiKey)
        }
    }
}