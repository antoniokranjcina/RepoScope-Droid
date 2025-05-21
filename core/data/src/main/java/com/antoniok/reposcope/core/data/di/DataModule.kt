package com.antoniok.reposcope.core.data.di

import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.data.repository.impl.OfflineFirstRepoScopeRepository
import com.antoniok.reposcope.core.datasource.local.RepoScopeLocalDataSource
import com.antoniok.reposcope.core.datasource.remote.RepoScopeRemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

public val dataModule: Module = module {
    single { provideRepoScopeRepository(get(), get()) }
}

private fun provideRepoScopeRepository(
    localDataSource: RepoScopeLocalDataSource,
    remoteDataSource: RepoScopeRemoteDataSource
): RepoScopeRepository = OfflineFirstRepoScopeRepository(
    localDataSource = localDataSource,
    remoteDataSource = remoteDataSource
)
