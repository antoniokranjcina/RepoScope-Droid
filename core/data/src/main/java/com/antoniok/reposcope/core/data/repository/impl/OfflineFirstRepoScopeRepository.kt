package com.antoniok.reposcope.core.data.repository.impl

import com.antoniok.reposcope.core.data.model.asEntity
import com.antoniok.reposcope.core.data.model.asExternalModel
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.datasource.local.RepoScopeLocalDataSource
import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import com.antoniok.reposcope.core.datasource.remote.RepoScopeRemoteDataSource
import com.antoniok.reposcope.core.datasource.remote.resource.NetworkResource
import com.antoniok.reposcope.core.model.GitHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class OfflineFirstRepoScopeRepository(
    private val localDataSource: RepoScopeLocalDataSource,
    private val remoteDataSource: RepoScopeRemoteDataSource
) : RepoScopeRepository {
    override val gitHubRepos: Flow<List<GitHubRepo>>
        get() = localDataSource.gitHubRepos.map {
            it.map(GitHubRepoEntity::asExternalModel)
        }

    override suspend fun getGitHubRepoById(id: Long): GitHubRepo? =
        localDataSource.gitHubRepoById(id)?.asExternalModel()

    override suspend fun sync(org: String): Boolean =
        when (val reposDto = remoteDataSource.getOrganizationRepos(org)) {
            is NetworkResource.Success -> {
                val remoteRepos = reposDto.data.map { it.asEntity() }.sortedBy { it.id }
                val localRepos = localDataSource.gitHubRepos.first().sortedBy { it.id }

                val sizeDiffers = localRepos.size != remoteRepos.size
                val contentDiffers = localRepos.zip(remoteRepos).any { (local, remote) ->
                    local.id != remote.id || (local.updatedAt ?: "") != (remote.updatedAt ?: "")
                }

                if (sizeDiffers || contentDiffers) {
                    localDataSource.upsertGitHubRepos(remoteRepos)
                }

                true
            }

            is NetworkResource.Error -> {
                reposDto.error.printStackTrace()
                false
            }
        }
}
