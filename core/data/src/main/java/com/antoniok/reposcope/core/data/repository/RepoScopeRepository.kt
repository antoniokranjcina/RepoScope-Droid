package com.antoniok.reposcope.core.data.repository

import com.antoniok.reposcope.core.data.Sync
import com.antoniok.reposcope.core.model.GitHubRepo
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing GitHub repository data within the application scope.
 *
 * This interface defines the contract for synchronizing and retrieving GitHub repositories
 * from a data source (e.g., remote API or local database). It provides a reactive flow of
 * repositories and a method to retrieve a specific repository by its ID.
 *
 * Implementations of this interface are expected to handle the underlying data synchronization
 * and persistence.
 */
public interface RepoScopeRepository : Sync {

    /**
     * A reactive [Flow] that emits the current list of [GitHubRepo]s.
     *
     * Observers can collect this flow to receive real-time updates whenever the list
     * of repositories changes.
     */
    public val gitHubRepos: Flow<List<GitHubRepo>>

    /**
     * Retrieves a [GitHubRepo] by its unique identifier.
     *
     * @param id The unique ID of the GitHub repository to retrieve.
     * @return The [GitHubRepo] if found, or `null` if no repository with the given ID exists.
     */
    public suspend fun getGitHubRepoById(id: Long): GitHubRepo?
}
