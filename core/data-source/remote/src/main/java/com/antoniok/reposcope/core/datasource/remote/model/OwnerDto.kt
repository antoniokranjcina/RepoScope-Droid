package com.antoniok.reposcope.core.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class OwnerDto(
    @SerialName("id")
    val id: Long,
    @SerialName("login")
    val login: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("html_url")
    val htmlUrl: String
)
