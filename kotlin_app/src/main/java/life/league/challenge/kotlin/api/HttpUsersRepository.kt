package life.league.challenge.kotlin.api

import arrow.core.Either
import arrow.core.computations.ResultEffect.bind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.UserDetails

class HttpUsersRepository(
    private val httpLoginRepository: HttpLoginRepository,
    private val api: Api
) {

    suspend fun fetchUserDetails(credentials: String) = Either.catch {
        val account = httpLoginRepository.login(credentials).bind()
        val users = fetchUsers(account.apiKey).bind()
        val posts = fetchPosts(account.apiKey).bind()
        val userDetails: List<UserDetails> = posts.map { post ->
            val user: User? = users.find { user -> user.id == post.userId }
            UserDetails(
                user?.avatar,
                user?.name ?: "",
                post.title,
                post.description
            )
        }
        userDetails
    }

    private suspend fun fetchUsers(apiKey: String?): Either<Throwable, List<User>> = Either.catch {
        withContext(Dispatchers.IO) {
            api.fetchUsers(apiKey)
        }
    }

    private suspend fun fetchPosts(apiKey: String?): Either<Throwable, List<Post>> =
        Either.catch {
            withContext(Dispatchers.IO) {
                api.fetchUserPosts(apiKey)
            }
        }
}