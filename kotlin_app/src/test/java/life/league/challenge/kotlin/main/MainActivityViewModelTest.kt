package life.league.challenge.kotlin.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import life.league.challenge.kotlin.CoroutineTestRule
import life.league.challenge.kotlin.api.HttpUsersRepository
import life.league.challenge.kotlin.model.User
import org.junit.Rule
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    val coroutineTestRule = CoroutineTestRule()

    private val testDispatcher = AppDispatchers(
            IO = TestCoroutineDispatcher(),
            MAIN = Dispatchers.Unconfined
    )

    @Test
    fun shouldCallRepositoryToLogin_whenLoginCalled() = runBlocking {
        val aUser = User("avatar", "name", "username", "email")
        val repository = mockk<HttpUsersRepository> {
            coEvery { fetchUsers(any()) } returns aUser
        }
        val encodedCredentials = "credentials"
        MainActivityViewModel(repository, encodedCredentials, testDispatcher)

        coVerify { repository.fetchUsers(encodedCredentials) }
    }

    @Test
    fun shouldUpdateUIStateToLoggedIn_whenLoginIsSuccessful() = runBlocking {
        val aUser = User("avatar", "name", "username", "email")
        val repository = mockk<HttpUsersRepository> {
            coEvery { fetchUsers(any()) } returns aUser
        }
        val viewModel = MainActivityViewModel(repository, "credentials", testDispatcher)

        assertEquals(MainActivityViewModel.UiState.LoggedOut, viewModel.uiState.value)
    }

    //TODO
    @Test
    fun shouldUpdateUIStateToError_whenLoginIsSuccessful() = runBlocking {
        val repository = mockk<HttpUsersRepository> {
            coEvery { fetchUsers(any()) } throws RuntimeException()
        }
        val viewModel = MainActivityViewModel(repository, "credentials", testDispatcher)

        assertEquals(MainActivityViewModel.UiState.Error, viewModel.uiState.value)
    }



}