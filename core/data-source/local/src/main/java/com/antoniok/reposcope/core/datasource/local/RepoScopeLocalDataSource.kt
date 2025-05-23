package com.antoniok.reposcope.core.datasource.local

import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import kotlinx.coroutines.flow.Flow

/**
 * A local data source interface responsible for managing GitHub repository data
 * stored in the device's local database.
 */
public interface RepoScopeLocalDataSource {

    /**
     * Inserts a GitHub repository into the local database.
     * If the repository already exists, it will be updated.
     *
     * @param repo The [GitHubRepoEntity] to be inserted or updated.
     * @return The row ID of the inserted or updated repository.
     */
    public suspend fun upsertGitHubRepo(repo: GitHubRepoEntity): Long

    /**
     * Inserts or updates a list of GitHub repositories in the local database.
     *
     * @param repos The list of [GitHubRepoEntity] objects to be inserted or updated.
     * @return A list of row IDs corresponding to each inserted or updated repository.
     */
    public suspend fun upsertGitHubRepos(repos: List<GitHubRepoEntity>): List<Long>

    /**
     * A cold [Flow] stream that emits the current list of GitHub repositories
     * stored in the local database. Emits a new list whenever the data changes.
     */
    public val gitHubRepos: Flow<List<GitHubRepoEntity>>

    /**
     * Retrieves a GitHub repository from the local database by its ID.
     *
     * @param id The unique identifier of the repository.
     * @return The matching [GitHubRepoEntity], or `null` if not found.
     */
    public suspend fun gitHubRepoById(id: Long): GitHubRepoEntity?

    /**
     * Deletes all GitHub repositories from the local database.
     *
     * @return The number of rows removed.
     */
    public suspend fun deleteGitHubRepos(): Int

    /**
     * Deletes a specific GitHub repository from the local database.
     *
     * @param repo The [GitHubRepoEntity] to be deleted.
     */
    public suspend fun deleteGitHubRepo(repo: GitHubRepoEntity)
}
