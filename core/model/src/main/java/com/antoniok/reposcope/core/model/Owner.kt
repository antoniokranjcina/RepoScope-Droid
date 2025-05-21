package com.antoniok.reposcope.core.model

/**
 * External data layer representation of an Owner
 */
public data class Owner(
    val id: Long,
    val login: String? = null,
    val avatarUrl: String,
    val htmlUrl: String
)
