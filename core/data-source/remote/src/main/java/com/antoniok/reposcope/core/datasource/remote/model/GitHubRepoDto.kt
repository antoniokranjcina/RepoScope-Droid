package com.antoniok.reposcope.core.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GitHubRepoDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("owner")
    val owner: OwnerDto,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("default_branch")
    val defaultBranch: String,
    @SerialName("fork")
    val fork: Boolean,
    @SerialName("disabled")
    val disabled: Boolean,
    @SerialName("watchers_count")
    val watchersCount: Int = 0,
    @SerialName("forks_count")
    val forksCount: Int = 0,
    @SerialName("stargazers_count")
    val stargazersCount: Int = 0,
    @SerialName("has_issues")
    val hasIssues: Boolean = false,
    @SerialName("open_issues_count")
    val openIssuesCount: Int = 0,
    @SerialName("archived")
    val archived: Boolean = false,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)
