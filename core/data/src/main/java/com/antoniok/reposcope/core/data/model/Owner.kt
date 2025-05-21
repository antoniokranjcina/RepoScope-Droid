package com.antoniok.reposcope.core.data.model

import com.antoniok.reposcope.core.datasource.local.entity.OwnerEntity
import com.antoniok.reposcope.core.model.Owner
import com.antoniok.reposcope.core.datasource.remote.model.OwnerDto

internal fun OwnerEntity.asExternalModel(): Owner = Owner(
    id = id,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)

internal fun OwnerDto.asEntity(): OwnerEntity = OwnerEntity(
    id = id,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)
