package life.league.challenge.kotlin.api

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.model.Account
import org.junit.Test

class HttpLoginRepositoryTest {

    @Test
    fun shouldCallApi_whenLoginCalled() = runBlocking {
        val api = mockk<Api> {
            coEvery { login(any()) } returns Account("apiKey")
        }
        val repository = HttpLoginRepository(api)

        repository.login("credentials")

        coVerify { api.login("Basic credentials") }
    }

}