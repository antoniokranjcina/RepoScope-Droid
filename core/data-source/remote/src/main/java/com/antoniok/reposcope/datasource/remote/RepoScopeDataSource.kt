package com.antoniok.reposcope.datasource.remote

import com.antoniok.reposcope.datasource.remote.model.GitHubRepo
import com.antoniok.reposcope.datasource.remote.resource.NetworkResource

/**
 * Data source interface for fetching GitHub repositories related to a specific organization.
 */
public interface RepoScopeDataSource {

    /**
     * Retrieves a list of public repositories for the given GitHub organization.
     *
     * @param org The organization name (e.g., "square").
     * @return [NetworkResource] wrapping a list of [GitHubRepo] on success, or an error on failure.
     */
    public suspend fun getOrganizationRepos(org: String): NetworkResource<List<GitHubRepo>>
}
