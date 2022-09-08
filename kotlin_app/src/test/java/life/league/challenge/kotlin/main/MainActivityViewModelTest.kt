package life.league.challenge.kotlin.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import life.league.challenge.kotlin.api.HttpUsersRepository
import life.league.challenge.kotlin.model.UserDetails
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun shouldCallRepository_WhenViewModelIsInitialized() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val encodedCredentials = "credentials"
        val repository = mockk<HttpUsersRepository>()

        MainActivityViewModel(repository, encodedCredentials)

        coVerify { repository.fetchUserDetails(encodedCredentials) }
    }

    @Test
    fun shouldUpdateUserDetailsLiveData_WhenViewModelIsInitialized() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val encodedCredentials = "credentials"
        val userList = listOf(UserDetails("avatar", "userName", "title", "description"))
        val repository = mockk<HttpUsersRepository> {
            coEvery { fetchUserDetails(any()) } returns userList
        }

        val viewModel = MainActivityViewModel(repository, encodedCredentials)

        val expectedUIState = MainActivityViewModel.UIState.Data(userList)
        assertEquals(expectedUIState, viewModel.uiState.value)
    }
}