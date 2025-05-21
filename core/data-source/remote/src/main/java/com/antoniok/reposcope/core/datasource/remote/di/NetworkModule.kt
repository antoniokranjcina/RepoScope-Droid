package com.antoniok.reposcope.core.datasource.remote.di

import com.antoniok.reposcope.core.datasource.remote.BuildConfig
import com.antoniok.reposcope.core.datasource.remote.RepoScopeDataSource
import com.antoniok.reposcope.core.datasource.remote.api.KtorGitHubNetwork
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

public val networkModule: Module = module {
    single {
        provideGitHubNetwork()
    }
}

private fun provideGitHubNetwork(): RepoScopeDataSource =
    KtorGitHubNetwork(
        client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        },
        baseUrl = BuildConfig.BASE_URL
    )
