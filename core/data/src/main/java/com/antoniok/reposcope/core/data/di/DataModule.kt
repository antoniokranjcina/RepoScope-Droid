package com.antoniok.reposcope.core.data.di

import android.content.Context
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.data.repository.impl.OfflineFirstRepoScopeRepository
import com.antoniok.reposcope.core.data.util.ConnectivityManagerNetworkMonitor
import com.antoniok.reposcope.core.data.util.NetworkMonitor
import com.antoniok.reposcope.core.datasource.local.RepoScopeLocalDataSource
import com.antoniok.reposcope.core.datasource.remote.RepoScopeRemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

public val dataModule: Module = module {
    single { provideRepoScopeRepository(get(), get()) }
    single { provideNetworkMonitor(context = get()) }
}

private fun provideRepoScopeRepository(
    localDataSource: RepoScopeLocalDataSource,
    remoteDataSource: RepoScopeRemoteDataSource
): RepoScopeRepository = OfflineFirstRepoScopeRepository(
    localDataSource = localDataSource,
    remoteDataSource = remoteDataSource
)

private fun provideNetworkMonitor(
    context: Context,
): NetworkMonitor = ConnectivityManagerNetworkMonitor(
    context = context
)
