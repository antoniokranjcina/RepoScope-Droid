package com.antoniok.reposcope.core.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class RepoScopeLocalStorageTest : DatabaseTest() {

    private val localDataSource by lazy { RepoScopeLocalStorage(repoScopeDao) }

    @Test
    fun upsertGitHubRepo_insertsAndReturnsRowId() = runTest {
        val repo = sampleRepo(id = 42L)

        val returnedId = localDataSource.upsertGitHubRepo(repo)
        assertThat(returnedId).isEqualTo(42L)

        val stored = repoScopeDao.getGitHubRepoById(42L)
        assertThat(stored).isEqualTo(repo)
    }

    @Test
    fun upsertGitHubRepos_insertsListAndReturnsAllIds() = runTest {
        val repos = listOf(sampleRepo(1), sampleRepo(2), sampleRepo(3))

        val returnedIds = localDataSource.upsertGitHubRepos(repos)
        assertThat(returnedIds).containsExactly(1L, 2L, 3L)

        assertThat(localDataSource.gitHubRepoById(2)).isEqualTo(sampleRepo(2))
    }

    @Test
    fun gitHubRepos_flowEmitsCurrentListAndSubsequentUpdates() = runTest {
        localDataSource.gitHubRepos.test {
            assertThat(awaitItem()).isEmpty()

            val r1 = sampleRepo(10)
            localDataSource.upsertGitHubRepo(r1)
            assertThat(awaitItem()).containsExactly(r1)

            val r2 = sampleRepo(20)
            localDataSource.upsertGitHubRepo(r2)
            assertThat(awaitItem()).containsExactly(r1, r2)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun gitHubRepoById_returnsNullWhenNotFound() = runTest {
        val result = localDataSource.gitHubRepoById(999L)
        assertThat(result).isNull()
    }

    @Test
    fun deleteGitHubRepo_removesOnlyThatRepo() = runTest {
        val repo1 = sampleRepo(1)
        val repo2 = sampleRepo(2)
        localDataSource.upsertGitHubRepos(listOf(repo1, repo2))

        localDataSource.deleteGitHubRepo(repo1)

        assertThat(localDataSource.gitHubRepoById(1)).isNull()
        assertThat(localDataSource.gitHubRepoById(2)).isEqualTo(repo2)
    }

    @Test
    fun deleteGitHubRepos_clearsAllAndReturnsCount() = runTest {
        val repos = listOf(sampleRepo(5), sampleRepo(6), sampleRepo(7))
        localDataSource.upsertGitHubRepos(repos)

        val deletedCount = localDataSource.deleteGitHubRepos()
        assertThat(deletedCount).isEqualTo(3)

        localDataSource.gitHubRepos.test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
