package com.antoniok.reposcope.feature.repositories.di

import com.antoniok.reposcope.feature.repositories.screen.repos.RepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoriesModule = module {
    viewModel { RepositoriesViewModel(get()) }
}
