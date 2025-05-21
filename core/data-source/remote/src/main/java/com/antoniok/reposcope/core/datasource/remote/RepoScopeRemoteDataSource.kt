package com.antoniok.reposcope.core.datasource.remote

import com.antoniok.reposcope.core.datasource.remote.model.GitHubRepoDto
import com.antoniok.reposcope.core.datasource.remote.resource.NetworkResource

/**
 * Data source interface for fetching GitHub repositories related to a specific organization.
 */
public interface RepoScopeRemoteDataSource {
    /**
     * Retrieves a list of public repositories for the given GitHub organization.
     *
     * @param org The organization name (e.g., "square").
     * @return [NetworkResource] wrapping a list of [GitHubRepoDto] on success, or an error on
     * failure.
     */
    public suspend fun getOrganizationRepos(org: String): NetworkResource<List<GitHubRepoDto>>
}
