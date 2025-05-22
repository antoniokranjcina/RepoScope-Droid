package com.antoniok.reposcope.feature.repositories

import app.cash.turbine.test
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.model.Owner
import com.antoniok.reposcope.feature.repositories.screen.details.DetailUiState
import com.antoniok.reposcope.feature.repositories.screen.details.RepositoryDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepositoryDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: RepoScopeRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emits Loading then Success when repository is found`() = runTest {
        val fakeRepo = GitHubRepo(
            id = 1L,
            name = "TestRepo",
            fullName = "user/TestRepo",
            owner = Owner(
                id = 1L,
                avatarUrl = "https://avatar.url",
                login = "https://github.com/user-login",
                htmlUrl = "https://github.com/user"
            ),
            htmlUrl = "https://github.com/user/TestRepo",
            description = "Test repo",
            language = "Kotlin",
            homepage = null,
            defaultBranch = "main",
            fork = false,
            disabled = false
        )

        coEvery { repository.getGitHubRepoById(1L) } returns fakeRepo

        val viewModel = RepositoryDetailViewModel(repository, 1L)

        viewModel.uiState.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(DetailUiState.Success(fakeRepo), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when repository is not found`() = runTest {
        coEvery { repository.getGitHubRepoById(2L) } returns null

        val viewModel = RepositoryDetailViewModel(repository, 2L)

        viewModel.uiState.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem()
            assertTrue(errorState is DetailUiState.Error)
            assertTrue((errorState as DetailUiState.Error).throwable is NoSuchElementException)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when repository throws an exception`() = runTest {
        val exception = RuntimeException("Network error")

        coEvery { repository.getGitHubRepoById(3L) } throws exception

        val viewModel = RepositoryDetailViewModel(repository, 3L)

        viewModel.uiState.test {
            assertEquals(DetailUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem()
            assertTrue(errorState is DetailUiState.Error)
            assertEquals(exception, (errorState as DetailUiState.Error).throwable)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
