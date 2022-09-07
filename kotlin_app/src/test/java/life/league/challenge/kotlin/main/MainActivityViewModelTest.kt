package life.league.challenge.kotlin.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import life.league.challenge.kotlin.api.HttpLoginRepository
import org.junit.Rule
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testScope = TestScope()

    @Test
    fun shouldCallRepositoryToLogin_whenLoginCalled() = runBlocking {
        val repository = mockk<HttpLoginRepository> {
            coEvery { login(any()) } returns Unit
        }
        val encodedCredentials = "credentials"
        val viewModel = MainActivityViewModel(repository, encodedCredentials)

        coVerify { repository.login(encodedCredentials) }
    }

    @Test
    fun shouldUpdateUIStateToLoggedIn_whenLoginIsSuccessful() = testScope.runTest {
        val repository = mockk<HttpLoginRepository> {
            coEvery { login(any()) } returns Unit
        }
        val viewModel = MainActivityViewModel(repository, "credentials", testScope.coroutineContext)

        assertEquals(MainActivityViewModel.UiState.LoggedOut, viewModel.uiState.value)
    }
}