package life.league.challenge.kotlin.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.left
import arrow.core.right
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
import life.league.challenge.kotlin.api.NoPostsFoundException
import life.league.challenge.kotlin.main.MainActivityViewModel.Companion.ERROR_TEXT_MESSAGE
import life.league.challenge.kotlin.main.MainActivityViewModel.Companion.NO_POST_FOUND_TEXT_MESSAGE
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
            coEvery { fetchUserDetails(any()) } returns userList.right()
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
            coEvery { fetchUserDetails(any()) } returns RuntimeException().left()
        }

        val viewModel = MainActivityViewModel(repository, encodedCredentials)

        val expectedUIState = MainActivityViewModel.UIState.Error(ERROR_TEXT_MESSAGE)
        assertEquals(expectedUIState, viewModel.uiState.value)
    }

    @Test
    fun shouldUpdateUIState_To_Error_WhenRepositoryFails_NoPostException() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val repository = mockk<HttpUsersRepository> {
            coEvery { fetchUserDetails(any()) } returns NoPostsFoundException().left()
        }

        val viewModel = MainActivityViewModel(repository, encodedCredentials)

        val expectedUIState = MainActivityViewModel.UIState.Error(NO_POST_FOUND_TEXT_MESSAGE)
        assertEquals(expectedUIState, viewModel.uiState.value)
    }
}