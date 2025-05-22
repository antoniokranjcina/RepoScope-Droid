package com.antoniok.reposcope.feature.repositories.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.model.GitHubRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class RepositoriesFeedViewModel(
    private val repoScopeRepository: RepoScopeRepository
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val feedUiState: StateFlow<FeedUiState> = _refreshTrigger
        .onStart { emit(Unit) }
        .flatMapLatest {
            repoScopeRepository.gitHubRepos
                .map { repos ->
                    if (repos.isEmpty()) {
                        FeedUiState.Empty
                    } else {
                        FeedUiState.Success(repos)
                    }
                }
                .onStart { emit(FeedUiState.Loading) }
                .catch { e -> emit(FeedUiState.Error(e)) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FeedUiState.Loading
        )

    fun refreshRepos() {
        viewModelScope.launch {
            _isRefreshing.value = true
            repoScopeRepository.sync(org = "square")
            _refreshTrigger.tryEmit(Unit)
            _isRefreshing.value = false
        }
    }
}

internal sealed interface FeedUiState {
    data class Success(val repos: List<GitHubRepo>) : FeedUiState
    data object Empty : FeedUiState
    data class Error(val throwable: Throwable? = null) : FeedUiState
    data object Loading : FeedUiState
}
