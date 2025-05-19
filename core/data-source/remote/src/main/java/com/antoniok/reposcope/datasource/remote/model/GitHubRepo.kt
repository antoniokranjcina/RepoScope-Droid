package com.antoniok.reposcope.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GitHubRepo(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("description")
    val description: String?,
    @SerialName("language")
    val language: String?
)
