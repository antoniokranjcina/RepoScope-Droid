package com.antoniok.reposcope.datasource.remote.api

import com.antoniok.reposcope.datasource.remote.RepoScopeDataSource
import com.antoniok.reposcope.datasource.remote.model.GitHubRepo
import com.antoniok.reposcope.datasource.remote.resource.NetworkResource
import com.antoniok.reposcope.datasource.remote.resource.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class KtorGitHubNetwork(
    private val client: HttpClient,
    private val baseUrl: String
) : RepoScopeDataSource {
    override suspend fun getOrganizationRepos(org: String): NetworkResource<List<GitHubRepo>> =
        safeApiCall {
            client.get("$baseUrl/orgs/$org/repos")
        }
}
