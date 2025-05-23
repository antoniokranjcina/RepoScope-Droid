package com.antoniok.reposcope.feature.repositories

import app.cash.turbine.test
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.model.Owner
import com.antoniok.reposcope.feature.repositories.screen.feed.FeedUiState
import com.antoniok.reposcope.feature.repositories.screen.feed.RepositoriesFeedViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepositoriesFeedDestinationViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

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

    private val fakeRepos = listOf(
        GitHubRepo(
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
    )

    @Test
    fun `feedUiState emits Loading then Success when repos are available`() = testScope.runTest {
        val reposFlow = MutableStateFlow(fakeRepos)
        every { repository.gitHubRepos } returns reposFlow

        val viewModel = RepositoriesFeedViewModel(repository)

        // Collect feedUiState
        viewModel.feedUiState.test {
            assertEquals(FeedUiState.Loading, awaitItem()) // initial loading
            assertEquals(FeedUiState.Success(fakeRepos), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `feedUiState emits Loading then Empty when no repos`() = testScope.runTest {
        val reposFlow = MutableStateFlow(emptyList<GitHubRepo>())
        every { repository.gitHubRepos } returns reposFlow

        val viewModel = RepositoriesFeedViewModel(repository)

        viewModel.feedUiState.test {
            assertEquals(FeedUiState.Loading, awaitItem())
            assertEquals(FeedUiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `feedUiState emits Loading then Error when repo flow throws`() = testScope.runTest {
        val exception = RuntimeException("Network failure")
        val errorFlow = flow<List<GitHubRepo>> { throw exception }
        every { repository.gitHubRepos } returns errorFlow

        val viewModel = RepositoriesFeedViewModel(repository)

        viewModel.feedUiState.test {
            assertEquals(FeedUiState.Loading, awaitItem())
            val errorState = awaitItem()
            assertTrue(errorState is FeedUiState.Error)
            assertEquals(exception, (errorState as FeedUiState.Error).throwable)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isRefreshing toggles correctly on refreshRepos call`() = testScope.runTest {
        val reposFlow = MutableStateFlow(fakeRepos)
        every { repository.gitHubRepos } returns reposFlow

        coEvery { repository.sync(any()) } returns true

        val viewModel = RepositoriesFeedViewModel(repository)

        // Initially not refreshing
        viewModel.isRefreshing.test {
            assertFalse(awaitItem()) // initial false

            // Call refreshRepos
            viewModel.refreshRepos()

            // Now refreshing should be true then false after sync completes
            assertTrue(awaitItem())
            assertFalse(awaitItem())

            cancelAndIgnoreRemainingEvents()
        }

        // Verify sync called once with DEFAULT_ORG
        coVerify(exactly = 1) { repository.sync(org = "square") }
    }

    @Test
    fun `feedUiState reloads after refreshRepos call`() = testScope.runTest {
        val reposFlow = MutableStateFlow(fakeRepos)
        every { repository.gitHubRepos } returns reposFlow
        coEvery { repository.sync(any()) } coAnswers {
            reposFlow.value = listOf(
                fakeRepos[0].copy(name = "UpdatedRepo")
            )
            true
        }

        val viewModel = RepositoriesFeedViewModel(repository)

        viewModel.feedUiState.test {
            assertEquals(FeedUiState.Loading, awaitItem())
            assertEquals(FeedUiState.Success(fakeRepos), awaitItem())

            viewModel.refreshRepos()
            testScheduler.advanceUntilIdle()

            assertEquals(
                FeedUiState.Success(
                    listOf(fakeRepos[0].copy(name = "UpdatedRepo"))
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refreshRepos emits false on sync failure`() = testScope.runTest {
        val reposFlow = MutableStateFlow(fakeRepos)
        every { repository.gitHubRepos } returns reposFlow

        coEvery { repository.sync(any()) } returns false

        val viewModel = RepositoriesFeedViewModel(repository)

        viewModel.isRefreshing.test {
            assertFalse(awaitItem())

            viewModel.refreshRepos()

            assertTrue(awaitItem())
            assertFalse(awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
