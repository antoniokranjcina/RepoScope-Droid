package com.antoniok.reposcope.core.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class RepoScopeDaoTest : DatabaseTest() {
    @Test
    fun upsertSingleRepo_andGetById_returnsCorrectRepo() = runTest {
        val repo = sampleRepo()
        repoScopeDao.upsertGitHubRepo(repo)

        val result = repoScopeDao.getGitHubRepoById(repo.id)

        assertThat(result).isEqualTo(repo)
    }

    @Test
    fun upsertMultipleRepos_andGetAll_returnsAll() = runTest {
        val repos = listOf(sampleRepo(1), sampleRepo(2), sampleRepo(3))
        repoScopeDao.upsertGitHubRepos(repos)

        val result = repoScopeDao.getGitHubRepos().first()

        assertThat(result).containsExactlyElementsIn(repos)
    }

    @Test
    fun upsertSameRepo_multipleTimes_replacesPrevious() = runTest {
        val repoV1 = sampleRepo().copy(name = "OldName")
        val repoV2 = sampleRepo().copy(name = "NewName")

        repoScopeDao.upsertGitHubRepo(repoV1)
        repoScopeDao.upsertGitHubRepo(repoV2)

        val result = repoScopeDao.getGitHubRepoById(repoV1.id)
        assertThat(result?.name).isEqualTo("NewName")
    }

    @Test
    fun deleteGitHubRepo_removesThatRepo() = runTest {
        val repo = sampleRepo()
        repoScopeDao.upsertGitHubRepo(repo)

        repoScopeDao.deleteGitHubRepo(repo)

        val result = repoScopeDao.getGitHubRepoById(repo.id)
        assertThat(result).isNull()
    }

    @Test
    fun deleteAllGitHubRepos_removesAllRepos() = runTest {
        val repos = listOf(sampleRepo(1), sampleRepo(2))
        repoScopeDao.upsertGitHubRepos(repos)

        val deletedCount = repoScopeDao.deleteGitHubRepos()

        val result = repoScopeDao.getGitHubRepos().first()
        assertThat(result).isEmpty()
        assertThat(deletedCount).isEqualTo(2)
    }

    @Test
    fun getGitHubRepos_emitsFlowWithUpdates() = runTest {
        val repo1 = sampleRepo(1)
        val repo2 = sampleRepo(2)

        val job = launch {
            repoScopeDao.getGitHubRepos().test {
                // Initial: empty list
                assertThat(awaitItem()).isEmpty()

                // Insert repo1
                repoScopeDao.upsertGitHubRepo(repo1)
                assertThat(awaitItem()).containsExactly(repo1)

                // Insert repo2
                repoScopeDao.upsertGitHubRepo(repo2)
                assertThat(awaitItem()).containsExactly(repo1, repo2)

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }
}
