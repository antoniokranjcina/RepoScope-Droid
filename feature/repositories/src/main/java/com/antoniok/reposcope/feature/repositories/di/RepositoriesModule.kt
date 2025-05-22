package com.antoniok.reposcope.feature.repositories.di

import com.antoniok.reposcope.feature.repositories.screen.details.RepositoryDetailViewModel
import com.antoniok.reposcope.feature.repositories.screen.feed.RepositoriesFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    viewModel { RepositoriesFeedViewModel(get()) }
    viewModel { (repoId: Long) ->
        RepositoryDetailViewModel(
            repository = get(),
            repoId = repoId
        )
    }
}
