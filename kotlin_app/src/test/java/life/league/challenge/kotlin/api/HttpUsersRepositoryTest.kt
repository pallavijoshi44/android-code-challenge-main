package life.league.challenge.kotlin.api

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import org.junit.jupiter.api.Test

class HttpUsersRepositoryTest {

    private val loginRepository = mockk<HttpLoginRepository>()
    private val api = mockk<Api>()
    private val repository = HttpUsersRepository(loginRepository, api)

    @Test
    fun shouldCallUserApi_whenUserDetailsAreFetched() = runBlocking {
        val aUser = User()
        val credentials = "credentials"
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials)

        repository.fetchUsers(credentials)

        coVerify { api.fetchUsers(credentials) }
    }

    @Test
    fun shouldCallPostsApi_whenFetchPostsCalled() = runBlocking {
        val post = Post(1, "title", "String")
        val credentials = "credentials"
        coEvery { api.fetchUserPosts(any()) } returns listOf(post)
        coEvery { loginRepository.login(any()) } returns Account(credentials)

        repository.fetchPosts(credentials)

        coVerify { api.fetchUserPosts(credentials) }
    }
}