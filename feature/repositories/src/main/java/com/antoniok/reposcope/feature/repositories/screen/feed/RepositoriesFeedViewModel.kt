package com.antoniok.reposcope.feature.repositories.screen.feed

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

internal class RepositoriesFeedViewModel(
    repoScopeRepository: RepoScopeRepository
) : ViewModel() {
    val feedUiState: StateFlow<FeedUiState> =
        repoScopeRepository.gitHubRepos
            .map {
                // TODO check here if there is an network error or is actually empty
                if (it.isEmpty()) {
                    FeedUiState.Empty
                } else {
                    FeedUiState.Success(it)
                }
            }
            .onStart { emit(FeedUiState.Loading) }
            .catch { emit(FeedUiState.Error(it)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FeedUiState.Loading,
            )
}

internal sealed interface FeedUiState {
    data class Success(val repos: List<GitHubRepo>) : FeedUiState
    data object Empty : FeedUiState
    data class Error(val throwable: Throwable? = null) : FeedUiState
    data object Loading : FeedUiState
}
