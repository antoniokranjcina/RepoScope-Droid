package com.antoniok.reposcope.core.model

/**
 * External data layer representation of a GitHubRepo
 */
public data class GitHubRepo(
    val id: Long,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val htmlUrl: String,
    val description: String?,
    val language: String?,
    val homepage: String?,
    val defaultBranch: String,
    val fork: Boolean,
    val disabled: Boolean,
    val watchersCount: Int = 0,
    val forksCount: Int = 0,
    val stargazersCount: Int = 0,
    val hasIssues: Boolean = false,
    val openIssuesCount: Int = 0,
    val archived: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
