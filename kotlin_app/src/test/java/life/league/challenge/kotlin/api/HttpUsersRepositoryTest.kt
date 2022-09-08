package life.league.challenge.kotlin.api

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.UserDetails
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
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
    fun shouldCallLoginRepository_whenFetchUserDetailsCalled() = runBlocking {
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials).right()
        coEvery { api.fetchUserPosts(any()) } returns listOf(aPost)

        repository.fetchUserDetails(credentials)

        coVerify { loginRepository.login(credentials) }
    }

    @Test
    fun shouldCallUserApi_whenFetchUserDetailsCalled() = runBlocking {
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials).right()
        coEvery { api.fetchUserPosts(any()) } returns listOf(aPost)

        repository.fetchUserDetails(credentials)

        coVerify { api.fetchUsers(credentials) }
    }

    @Test
    fun shouldCallPostsApi_whenFetchUserDetailsCalled() = runBlocking {
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials).right()
        coEvery { api.fetchUserPosts(any()) } returns listOf(aPost)

        repository.fetchUserDetails(credentials)

        coVerify { api.fetchUserPosts(credentials) }
    }

    @Test
    fun shouldReturnListOfUserDetails_whenFetchUserDetailsCalled() = runBlocking {
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { api.fetchUserPosts(any()) } returns listOf(aPost)
        coEvery { loginRepository.login(any()) } returns Account(credentials).right()

        val result = repository.fetchUserDetails(credentials)

        val expectedListOfUserDetails = listOf(UserDetails(avatar, username, title, description))
        assertThat(result, `is`(expectedListOfUserDetails.right()))
    }

    @Test
    fun shouldThrowNoPostFoundException_whenNoPostsFound() = runBlocking {
        val aPostWithDifferentUserId = Post(2, "aTitle", "aDescription")
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { api.fetchUserPosts(any()) } returns listOf(aPostWithDifferentUserId)
        coEvery { loginRepository.login(any()) } returns Account(credentials).right()

        val result = repository.fetchUserDetails(credentials)

        assertThat(
            (result as Either.Left).value,
            `is`(instanceOf(NoPostsFoundException::class.java))
        )
    }

    @Test
    fun shouldReturnException_whenLoginRepoFails() = runBlocking {
        val exception = RuntimeException()
        coEvery { loginRepository.login(any()) } returns exception.left()

        val result = repository.fetchUserDetails(credentials)

        assertThat(result, `is`(exception.left()))
    }


    @Test
    fun shouldReturnException_whenUserApiFails() = runBlocking {
        val exception = RuntimeException()
        coEvery { api.fetchUsers(any()) } throws exception
        coEvery { loginRepository.login(any()) } returns Account(credentials).right()

        val result = repository.fetchUserDetails(credentials)

        assertThat(
            (result as Either.Left).value,
            `is`(instanceOf(RuntimeException::class.java))
        )
    }

    @Test
    fun shouldReturnException_whenPostApiFails() = runBlocking {
        val exception = RuntimeException()
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials).right()
        coEvery { api.fetchUserPosts(any()) } throws exception

        val result = repository.fetchUserDetails(credentials)


        assertThat(
            (result as Either.Left).value,
            `is`(instanceOf(RuntimeException::class.java))
        )
    }
}