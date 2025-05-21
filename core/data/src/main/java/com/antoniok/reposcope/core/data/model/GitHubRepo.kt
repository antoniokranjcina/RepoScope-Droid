package com.antoniok.reposcope.core.data.model

import com.antoniok.reposcope.core.datasource.local.entity.GitHubRepoEntity
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.datasource.remote.model.GitHubRepoDto

internal fun GitHubRepoEntity.asExternalModel(): GitHubRepo = GitHubRepo(
    id = id,
    name = name,
    fullName = fullName,
    owner = owner.asExternalModel(),
    htmlUrl = htmlUrl,
    description = description,
    language = language,
)

internal fun GitHubRepoDto.asEntity(): GitHubRepoEntity = GitHubRepoEntity(
    id = id,
    name = name,
    fullName = fullName,
    owner = owner.asEntity(),
    htmlUrl = htmlUrl,
    description = description,
    language = language,
)
