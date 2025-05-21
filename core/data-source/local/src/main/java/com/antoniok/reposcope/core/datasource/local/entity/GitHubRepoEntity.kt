package com.antoniok.reposcope.core.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.antoniok.reposcope.core.datasource.local.util.Table

@Entity(
    tableName = Table.GITHUB_REPO,
    indices = [Index(value = ["repo_id"], unique = true)]
)
public data class GitHubRepoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "repo_id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @Embedded
    val owner: OwnerEntity,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "language")
    val language: String?
)
