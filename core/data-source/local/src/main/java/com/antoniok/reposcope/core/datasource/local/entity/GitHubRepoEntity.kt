package com.antoniok.reposcope.core.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.antoniok.reposcope.core.datasource.local.util.Table

@Entity(
    tableName = Table.GITHUB_REPO,
    indices = [Index(value = ["repo_id"], unique = true)]
)
public data class GitHubRepoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "repo_id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @Embedded
    val owner: OwnerEntity,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "language")
    val language: String?,
    @ColumnInfo(name = "homepage")
    val homepage: String?,
    @ColumnInfo(name = "default_branch")
    val defaultBranch: String,
    @ColumnInfo(name = "fork")
    val fork: Boolean,
    @ColumnInfo(name = "disabled")
    val disabled: Boolean,
    @ColumnInfo(name = "watchers_count")
    val watchersCount: Int = 0,
    @ColumnInfo(name = "forks_count")
    val forksCount: Int = 0,
    @ColumnInfo(name = "stargazers_count")
    val stargazersCount: Int = 0,
    @ColumnInfo(name = "has_issues")
    val hasIssues: Boolean = false,
    @ColumnInfo(name = "open_issues_count")
    val openIssuesCount: Int = 0,
    @ColumnInfo(name = "archived")
    val archived: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null
)
