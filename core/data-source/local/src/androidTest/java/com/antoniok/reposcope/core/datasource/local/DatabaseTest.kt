package com.antoniok.reposcope.core.datasource.local

import androidx.room.Room
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.antoniok.reposcope.core.datasource.local.dao.RepoScopeDao
import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import com.antoniok.reposcope.core.datasource.local.entity.OwnerEntity
import com.antoniok.reposcope.core.datasource.local.RepoScopeDatabase
import org.junit.After
import org.junit.Before

internal abstract class DatabaseTest {
    private lateinit var db: RepoScopeDatabase
    protected lateinit var repoScopeDao: RepoScopeDao

    @Before
    fun setup() {
        db = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context,
                RepoScopeDatabase::class.java,
            ).build()
        }
        repoScopeDao = db.repoScopeDao
    }

    @After
    fun teardown() = db.close()

    protected fun sampleRepo(id: Long = 1L) = GitHubRepoEntity(
        id = id,
        name = "TestRepo",
        fullName = "owner/TestRepo",
        htmlUrl = "https://github.com/owner/TestRepo",
        description = "Test description",
        language = "Kotlin",
        owner = OwnerEntity(
            id = id,
            avatarUrl = "https://avatar.com/$id",
            htmlUrl = "https://github.com/owner"
        ),
        homepage = null,
        defaultBranch = "master",
        fork = true,
        disabled = false
    )
}
