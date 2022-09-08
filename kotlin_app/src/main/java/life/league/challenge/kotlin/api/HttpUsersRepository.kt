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

    suspend fun fetchUserDetails(credentials: String): Either<Throwable, List<UserDetails>> =
        Either.catch {
            val userDetails = mutableListOf<UserDetails>()
            //fetch api key to login
            val account = httpLoginRepository.login(credentials).bind()
            // fetch all users
            val users = fetchUsers(account.apiKey).bind()
            // fetch all posts
            val posts = fetchPosts(account.apiKey).bind()
            // find user corresponding to the post with below logic and create UserDetails list
            posts.forEach { post ->
                val user = users.find { user -> user.id == post.userId }
                user?.let {
                    userDetails.add(
                        UserDetails(
                            user.avatar,
                            user.name,
                            post.title,
                            post.description
                        )
                    )
                }
            }
            //custom exception to show different error to user if no posts are found
            if (userDetails.isEmpty()) throw NoPostsFoundException()
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