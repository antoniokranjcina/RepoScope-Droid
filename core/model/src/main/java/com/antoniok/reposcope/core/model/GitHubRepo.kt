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
    val language: String?
)
