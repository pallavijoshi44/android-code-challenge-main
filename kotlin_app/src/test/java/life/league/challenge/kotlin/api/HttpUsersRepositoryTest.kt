package life.league.challenge.kotlin.api

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.UserDetails
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class HttpUsersRepositoryTest {

    private val loginRepository = mockk<HttpLoginRepository>()
    private val api = mockk<Api>()
    private val repository = HttpUsersRepository(loginRepository, api)
    private val credentials = "credentials"
    private val userId = 1
    private val avatar = "avatar"
    private val username = "userName"
    private val aUser = User(userId, avatar, username)
    private val title = "title"
    private val description = "description"
    private val aPost = Post(userId, title, description)

    @Test
    fun shouldCallUserApi_whenFetchUserDetailsCalled() = runBlocking {
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials)
        coEvery { api.fetchUserPosts(any(), any()) } returns listOf(aPost)

        repository.fetchUserDetails(credentials)

        coVerify { api.fetchUsers(credentials) }
    }

    @Test
    fun shouldCallPostsApi_whenFetchUserDetailsCalled() = runBlocking {
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials)
        coEvery { api.fetchUserPosts(any(), any()) } returns listOf(aPost)

        repository.fetchUserDetails(credentials)

        coVerify { api.fetchUserPosts(credentials, userId) }
    }

    @Test
    fun shouldReturnListOfUsers_whenFetchUserDetailsCalled() = runBlocking {
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { api.fetchUserPosts(any(), any()) } returns listOf(aPost)
        coEvery { loginRepository.login(any()) } returns Account(credentials)

        val result = repository.fetchUserDetails(credentials)

        val expectedListOfUserDetails = listOf(UserDetails(avatar, username, title, description))
        assertThat(result, `is`(expectedListOfUserDetails))
    }
}