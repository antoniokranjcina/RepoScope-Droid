package com.antoniok.reposcope.core.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antoniok.reposcope.core.datasource.local.dao.RepoScopeDao
import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import com.antoniok.reposcope.core.datasource.local.entity.OwnerEntity

@Database(
    entities = [
        GitHubRepoEntity::class,
        OwnerEntity::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class RepoScopeDatabase : RoomDatabase() {
    abstract val repoScopeDao: RepoScopeDao
}
