package life.league.challenge.kotlin.api

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.model.Account
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class HttpLoginRepositoryTest {

    val anAccount = Account("apiKey")
    val api = mockk<Api>()
    val repository = HttpLoginRepository(api)

    @Test
    fun shouldCallApi_whenLoginCalled() = runBlocking {
        coEvery { api.login(any()) } returns anAccount

        repository.login("credentials")

        coVerify { api.login("Basic credentials") }
    }

    @Test
    fun shouldReturnAccountWithApiKey_whenLoginCalled() = runBlocking {
        coEvery { api.login(any()) } returns anAccount

        val result = repository.login("credentials")

        assertThat(result, `is`(anAccount))

    }

}