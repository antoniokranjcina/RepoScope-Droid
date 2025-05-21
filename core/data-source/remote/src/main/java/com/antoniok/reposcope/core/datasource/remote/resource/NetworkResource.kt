package com.antoniok.reposcope.core.datasource.remote.resource

public sealed class NetworkResource<T> {
    public data class Success<T>(val data: T) : NetworkResource<T>()
    public data class Error<T>(
        val error: Exception,
        val message: String? = null
    ) : NetworkResource<T>()
}
