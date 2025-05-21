package com.antoniok.reposcope.feature.repositories.screen.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.model.GitHubRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class RepositoriesViewModel(
    repoScopeRepository: RepoScopeRepository
) : ViewModel() {
    val repositoriesUiState: StateFlow<RepositoriesUiState> =
        repoScopeRepository.gitHubRepos
            .map<List<GitHubRepo>, RepositoriesUiState>(RepositoriesUiState::Success)
            .onStart { emit(RepositoriesUiState.Loading) }
            .catch { emit(RepositoriesUiState.Error(it)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RepositoriesUiState.Loading,
            )
}

sealed interface RepositoriesUiState {
    data class Success(val gitHubRepositories: List<GitHubRepo>) : RepositoriesUiState
    data class Error(val throwable: Throwable? = null) : RepositoriesUiState
    data object Loading : RepositoriesUiState
}
