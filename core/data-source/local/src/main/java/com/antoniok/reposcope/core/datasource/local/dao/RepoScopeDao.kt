package com.antoniok.reposcope.core.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RepoScopeDao {
    @Upsert
    suspend fun upsertGitHubRepo(repo: GitHubRepoEntity): Long

    @Upsert
    suspend fun upsertGitHubRepos(repos: List<GitHubRepoEntity>): List<Long>

    @Query("SELECT * FROM github_repo_entity")
    fun getGitHubRepos(): Flow<List<GitHubRepoEntity>>

    @Query("SELECT * FROM github_repo_entity WHERE repo_id = :id")
    suspend fun getGitHubRepoById(id: Long): GitHubRepoEntity?

    @Query("DELETE FROM github_repo_entity")
    suspend fun deleteGitHubRepos(): Int

    @Delete
    suspend fun deleteGitHubRepo(repo: GitHubRepoEntity)
}
