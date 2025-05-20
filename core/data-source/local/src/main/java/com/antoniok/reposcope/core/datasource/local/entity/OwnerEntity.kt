package com.antoniok.reposcope.core.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.antoniok.reposcope.core.datasource.local.util.Table

@Entity(tableName = Table.OWNER)
public data class OwnerEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "owner_id")
    val id: Long,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "owner_html_url")
    val htmlUrl: String
)
