package com.antoniok.reposcope.feature.repositories.screen.repos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.ui.GitHubPreviewParameterProvider
import com.antoniok.reposcope.feature.repositories.component.GithubRepoItem
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RepositoriesScreen(
    onNavigateToDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RepositoriesViewModel = koinViewModel(),
) {
    val uiState by viewModel.repositoriesUiState.collectAsStateWithLifecycle()

    RepositoriesContent(
        uiState = uiState,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@Composable
private fun RepositoriesContent(
    uiState: RepositoriesUiState,
    onNavigateToDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is RepositoriesUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is RepositoriesUiState.Error -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("An error occurred. Please try again.")
            }
        }

        is RepositoriesUiState.Success -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(uiState.gitHubRepositories) { repo ->
                    GithubRepoItem(
                        repo = repo,
                        onClick = { onNavigateToDetails(repo.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RepositoriesContentPreview(
    @PreviewParameter(GitHubPreviewParameterProvider::class)
    gitHubRepositories: List<GitHubRepo>
) {
    RepositoriesContent(
        uiState = RepositoriesUiState.Success(gitHubRepositories),
        onNavigateToDetails = {}
    )
}