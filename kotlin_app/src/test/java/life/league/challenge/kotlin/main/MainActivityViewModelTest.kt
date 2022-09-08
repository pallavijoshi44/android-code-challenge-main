package life.league.challenge.kotlin.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import life.league.challenge.kotlin.api.HttpUsersRepository
import life.league.challenge.kotlin.model.UserDetails
import org.junit.Rule
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val encodedCredentials = "credentials"

    @Test
    fun shouldCallRepository_WhenViewModelIsInitialized() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val repository = mockk<HttpUsersRepository>()

        MainActivityViewModel(repository, encodedCredentials)

        coVerify { repository.fetchUserDetails(encodedCredentials) }
    }

    @Test
    fun shouldUpdateUIState_To_Data_WhenRepositorySuccess() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val userList = listOf(UserDetails("avatar", "userName", "title", "description"))
        val repository = mockk<HttpUsersRepository> {
            coEvery { fetchUserDetails(any()) } returns userList
        }

        val viewModel = MainActivityViewModel(repository, encodedCredentials)

        val expectedUIState = MainActivityViewModel.UIState.Data(userList)
        assertEquals(expectedUIState, viewModel.uiState.value)
    }

    @Test
    fun shouldUpdateUIState_To_Error_WhenRepositoryFails() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val repository = mockk<HttpUsersRepository> {
            coEvery { fetchUserDetails(any()) } throws RuntimeException()
        }

        val viewModel = MainActivityViewModel(repository, encodedCredentials)

        val expectedUIState = MainActivityViewModel.UIState.Error
        assertEquals(expectedUIState, viewModel.uiState.value)
    }
}