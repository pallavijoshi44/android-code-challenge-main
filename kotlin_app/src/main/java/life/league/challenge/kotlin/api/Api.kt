package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface Api {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Account

    @GET("users")
    suspend fun fetchUsers(@Header("x-access-token") accessToken: String?): List<User>

    @GET("posts")
    suspend fun fetchUserPosts(@Header("x-access-token") accessToken: String?): List<Post>
}
