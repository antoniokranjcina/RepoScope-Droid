package com.antoniok.reposcope.core.datasource.local.di

import android.content.Context
import androidx.room.Room
import com.antoniok.reposcope.core.datasource.local.RepoScopeDatabase
import com.antoniok.reposcope.core.datasource.local.RepoScopeLocalDataSource
import com.antoniok.reposcope.core.datasource.local.RepoScopeLocalStorage
import com.antoniok.reposcope.core.datasource.local.dao.RepoScopeDao
import com.antoniok.reposcope.core.datasource.local.util.Table
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

public val localModule: Module = module {
    single { provideDatabase(context = androidContext()) }
    single { provideRepoScopeDao(get()) }
    single { provideRepoScopeLocalDataSource(get()) }
}

private fun provideDatabase(context: Context): RepoScopeDatabase = Room
    .databaseBuilder(
        context = context,
        klass = RepoScopeDatabase::class.java,
        name = Table.DATABASE_NAME
    )
    .build()

private fun provideRepoScopeDao(db: RepoScopeDatabase): RepoScopeDao = db.repoScopeDao

private fun provideRepoScopeLocalDataSource(
    repoScopeDao: RepoScopeDao
): RepoScopeLocalDataSource = RepoScopeLocalStorage(
    repoScopeDao = repoScopeDao
)
