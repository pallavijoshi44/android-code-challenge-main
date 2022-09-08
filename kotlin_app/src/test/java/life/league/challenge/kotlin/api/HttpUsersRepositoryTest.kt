package life.league.challenge.kotlin.api

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.User
import org.junit.jupiter.api.Test

class HttpUsersRepositoryTest {

    private val loginRepository = mockk<HttpLoginRepository>()
    private val api = mockk<Api>()
    private val repository = HttpUsersRepository(loginRepository, api)

    @Test
    fun shouldCallApi_whenUserDetailsAreFetched() = runBlocking {
        val aUser = User("avatar", "name", "username", "email")
        val credentials = "credentials"
        coEvery { api.fetchUsers(any()) } returns listOf(aUser)
        coEvery { loginRepository.login(any()) } returns Account(credentials)

        repository.fetchUsers(credentials)

        coVerify { api.fetchUsers(credentials) }
    }
}