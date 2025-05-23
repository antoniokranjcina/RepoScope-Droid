package com.antoniok.reposcope.core.data.repository

import app.cash.turbine.test
import com.antoniok.reposcope.core.data.model.asEntity
import com.antoniok.reposcope.core.data.model.asExternalModel
import com.antoniok.reposcope.core.data.repository.impl.OfflineFirstRepoScopeRepository
import com.antoniok.reposcope.core.datasource.local.RepoScopeLocalDataSource
import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import com.antoniok.reposcope.core.datasource.local.entity.OwnerEntity
import com.antoniok.reposcope.core.datasource.remote.RepoScopeRemoteDataSource
import com.antoniok.reposcope.core.datasource.remote.model.GitHubRepoDto
import com.antoniok.reposcope.core.datasource.remote.model.OwnerDto
import com.antoniok.reposcope.core.datasource.remote.resource.NetworkResource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class OfflineFirstRepoScopeRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private val localDataSource = mockk<RepoScopeLocalDataSource>()
    private val remoteDataSource = mockk<RepoScopeRemoteDataSource>()
    private lateinit var repository: OfflineFirstRepoScopeRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = OfflineFirstRepoScopeRepository(localDataSource, remoteDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `gitHubRepos emits mapped data`() = runTest {
        val expected = localEntity.asExternalModel()
        every { localDataSource.gitHubRepos } returns flowOf(listOf(localEntity))

        repository.gitHubRepos.test {
            assertEquals(listOf(expected), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getGitHubRepoById returns mapped data`() = runTest {
        val expected = localEntity.asExternalModel()
        coEvery { localDataSource.gitHubRepoById(1) } returns localEntity

        val result = repository.getGitHubRepoById(1)

        assertEquals(expected, result)
    }

    @Test
    fun `sync returns true and updates local data if content differs`() = runTest {
        coEvery { remoteDataSource.getOrganizationRepos("org") } returns NetworkResource.Success(
            listOf(remoteDto)
        )
        coEvery { localDataSource.gitHubRepos } returns flowOf(listOf(localEntity))
        coEvery { localDataSource.upsertGitHubRepos(any()) } returns listOf(1L)

        val result = repository.sync("org")

        assertTrue(result)
        coVerify { localDataSource.upsertGitHubRepos(listOf(remoteDto.asEntity())) }
    }

    @Test
    fun `sync returns false on network error`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { remoteDataSource.getOrganizationRepos("org") } returns NetworkResource.Error(
            exception
        )

        val result = repository.sync("org")

        assertFalse(result)
    }

    companion object {
        val localEntity = GitHubRepoEntity(
            id = 1,
            name = "Repo",
            fullName = "owner/TestRepo",
            htmlUrl = "https://github.com/owner/TestRepo",
            description = "Test description",
            language = "Kotlin",
            owner = OwnerEntity(
                id = 1,
                avatarUrl = "https://avatar.com/1",
                htmlUrl = "https://github.com/owner"
            ),
            homepage = null,
            defaultBranch = "master",
            fork = true,
            disabled = false,
            updatedAt = "2023-01-01"
        )

        val remoteDto = GitHubRepoDto(
            id = 1,
            name = "Repo",
            fullName = "owner/TestRepo",
            htmlUrl = "https://github.com/owner/TestRepo",
            description = "Test description",
            language = "Kotlin",
            owner = OwnerDto(
                id = 1,
                avatarUrl = "https://avatar.com/1",
                htmlUrl = "https://github.com/owner"
            ),
            homepage = null,
            defaultBranch = "master",
            fork = true,
            disabled = false,
            updatedAt = "2024-01-01"
        )
    }
}
