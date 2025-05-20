package com.antoniok.reposcope.core.datasource.local

import com.antoniok.reposcope.core.datasource.local.dao.RepoScopeDao
import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import kotlinx.coroutines.flow.Flow

internal class RepoScopeLocalStorage(
    private val repoScopeDao: RepoScopeDao
): RepoScopeLocalDataSource {
    override suspend fun upsertGitHubRepo(repo: GitHubRepoEntity): Long =
        repoScopeDao.upsertGitHubRepo(repo)

    override suspend fun upsertGitHubRepos(repos: List<GitHubRepoEntity>): List<Long> =
        repoScopeDao.upsertGitHubRepos(repos)

    override val gitHubRepos: Flow<List<GitHubRepoEntity>>
        get() = repoScopeDao.getGitHubRepos()

    override suspend fun gitHubRepoById(id: Long): GitHubRepoEntity? =
        repoScopeDao.getGitHubRepoById(id)

    override suspend fun deleteGitHubRepos(): Int =
        repoScopeDao.deleteGitHubRepos()

    override suspend fun deleteGitHubRepo(repo: GitHubRepoEntity) {
        repoScopeDao.deleteGitHubRepo(repo)
    }
}
