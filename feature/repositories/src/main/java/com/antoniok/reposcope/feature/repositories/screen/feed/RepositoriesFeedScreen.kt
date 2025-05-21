package com.antoniok.reposcope.feature.repositories.screen.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.ui.GitHubReposPreviewParameterProvider
import com.antoniok.reposcope.feature.repositories.component.GithubRepoItem
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object RepositoriesFeed

internal fun NavGraphBuilder.repositoriesScreen(
    onNavigateToDetails: (id: Long) -> Unit
) {
    composable<RepositoriesFeed> {
        RepositoriesFeedScreen(
            onNavigateToDetails = onNavigateToDetails
        )
    }
}

@Composable
internal fun RepositoriesFeedScreen(
    onNavigateToDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RepositoriesFeedViewModel = koinViewModel(),
) {
    val uiState by viewModel.feedUiState.collectAsStateWithLifecycle()

    RepositoriesFeedContent(
        uiState = uiState,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@Composable
private fun RepositoriesFeedContent(
    uiState: FeedUiState,
    onNavigateToDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is FeedUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FeedUiState.Error -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("An error occurred. Please try again.")
            }
        }

        is FeedUiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(
                    items = uiState.repos,
                    key = { it.id }
                ) { repo ->
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
private fun RepositoriesFeedContentPreview(
    @PreviewParameter(GitHubReposPreviewParameterProvider::class)
    gitHubRepositories: List<GitHubRepo>
) {
    RepositoriesFeedContent(
        uiState = FeedUiState.Success(gitHubRepositories),
        onNavigateToDetails = {}
    )
}