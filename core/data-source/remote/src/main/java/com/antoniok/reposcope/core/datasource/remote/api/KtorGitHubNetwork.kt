package com.antoniok.reposcope.core.datasource.remote.api

import com.antoniok.reposcope.core.datasource.remote.RepoScopeRemoteDataSource
import com.antoniok.reposcope.core.datasource.remote.model.GitHubRepoDto
import com.antoniok.reposcope.core.datasource.remote.resource.NetworkResource
import com.antoniok.reposcope.core.datasource.remote.resource.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class KtorGitHubNetwork(
    private val client: HttpClient,
    private val baseUrl: String
) : RepoScopeRemoteDataSource {
    override suspend fun getOrganizationRepos(org: String): NetworkResource<List<GitHubRepoDto>> =
        safeApiCall {
            client.get("$baseUrl/orgs/$org/repos")
        }
}
