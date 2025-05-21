package com.antoniok.reposcope.feature.repositories.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import com.antoniok.reposcope.core.model.GitHubRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class RepositoryDetailViewModel(
    private val repository: RepoScopeRepository,
    private val repoId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        fetchRepositoryDetails()
    }

    private fun fetchRepositoryDetails() {
        viewModelScope.launch {
            try {
                val repo = repository.getGitHubRepoById(repoId)
                _uiState.value = repo?.let {
                    DetailUiState.Success(it)
                } ?: run {
                    DetailUiState.Error(
                        NoSuchElementException("Repository with id=$repoId not found")
                    )
                }
            } catch (e: Throwable) {
                _uiState.value = DetailUiState.Error(e)
            }
        }
    }
}

sealed interface DetailUiState {
    data class Success(val repo: GitHubRepo) : DetailUiState
    data class Error(val throwable: Throwable? = null) : DetailUiState
    data object Loading : DetailUiState
}
