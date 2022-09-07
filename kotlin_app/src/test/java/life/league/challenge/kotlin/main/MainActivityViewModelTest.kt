package life.league.challenge.kotlin.main

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.api.HttpLoginRepository
import life.league.challenge.kotlin.model.Account
import org.junit.Test

class MainActivityViewModelTest {

    @Test
    fun shouldCallRepositoryToLogin_whenLoginCalled() = runBlocking {
        val repository = mockk<HttpLoginRepository> {
            coEvery { login(any()) } returns Unit
        }
        val encodedCredentials = "credentials"
        val viewModel = MainActivityViewModel(repository, encodedCredentials)

        viewModel.login()

        coVerify { repository.login(encodedCredentials) }
    }
}