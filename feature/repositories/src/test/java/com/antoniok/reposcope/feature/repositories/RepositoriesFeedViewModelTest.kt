package com.antoniok.reposcope.feature.repositories

import app.cash.turbine.test
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.model.Owner
import com.antoniok.reposcope.feature.repositories.screen.feed.FeedUiState
import com.antoniok.reposcope.feature.repositories.screen.feed.RepositoriesFeedViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepositoriesFeedViewModelTest {

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

    @Test
    fun `emits Loading then Success when repos are available`() = testScope.runTest {
        val fakeRepos = listOf(
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

        every { repository.gitHubRepos } returns flowOf(fakeRepos)

        val viewModel = RepositoriesFeedViewModel(repository)

        viewModel.feedUiState.test {
            assertEquals(FeedUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(FeedUiState.Success(fakeRepos), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Empty when no repos are available`() = testScope.runTest {
        every { repository.gitHubRepos } returns flowOf(emptyList())

        val viewModel = RepositoriesFeedViewModel(repository)

        viewModel.feedUiState.test {
            assertEquals(FeedUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(FeedUiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when repository flow throws exception`() = testScope.runTest {
        val exception = RuntimeException("Network failure")

        every { repository.gitHubRepos } returns flow { throw exception }

        val viewModel = RepositoriesFeedViewModel(repository)

        viewModel.feedUiState.test {
            assertEquals(FeedUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem()
            assertTrue(errorState is FeedUiState.Error)
            assertEquals(exception, (errorState as FeedUiState.Error).throwable)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
