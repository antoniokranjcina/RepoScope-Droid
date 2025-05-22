package com.antoniok.reposcope.feature.repositories.screen.feed

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.ui.GitHubReposPreviewParameterProvider
import com.antoniok.reposcope.feature.repositories.R
import com.antoniok.reposcope.feature.repositories.component.GithubRepoItem
import com.antoniok.reposcope.feature.repositories.screen.component.ErrorScreenContent
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
private fun RepositoriesFeedScreen(
    onNavigateToDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RepositoriesFeedViewModel = koinViewModel(),
) {
    val uiState by viewModel.feedUiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    RepositoriesFeedScreenContent(
        uiState = uiState,
        isRefreshing = isRefreshing,
        onNavigateToDetails = onNavigateToDetails,
        onRefresh = { viewModel.refreshRepos() },
        onRetry = {},
        modifier = modifier
    )
}

@Composable
private fun RepositoriesFeedScreenContent(
    uiState: FeedUiState,
    isRefreshing: Boolean,
    onNavigateToDetails: (id: Long) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = uiState,
        label = "feed-ui-state"
    ) { state ->
        when (state) {
            is FeedUiState.Success -> RepositoriesFeedContent(
                isRefreshing = isRefreshing,
                repos = state.repos,
                onNavigateToDetails = onNavigateToDetails,
                onRefresh = onRefresh,
                modifier = modifier
            )

            FeedUiState.Empty -> RepositoriesFeedEmptyContent()

            is FeedUiState.Error -> RepositoriesFeedErrorContent(
                onRetry = onRetry,
                modifier = modifier
            )

            FeedUiState.Loading -> RepositoriesFeedLoadingContent(modifier = modifier)
        }
    }
}

@Composable
private fun RepositoriesFeedLoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoriesFeedContent(
    isRefreshing: Boolean,
    repos: List<GitHubRepo>, // TODO make this immutable
    onNavigateToDetails: (id: Long) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pullRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(
                items = repos,
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

@Composable
fun RepositoriesFeedEmptyContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Inbox,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = stringResource(R.string.repo_feed_empty_title),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(R.string.repo_feed_empty_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun RepositoriesFeedErrorContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    ErrorScreenContent(
        subtitle = stringResource(id = R.string.repo_feed_error_subtitle),
        onRetry = onRetry,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun RepositoriesFeedLoadingContentPreview() {
    MaterialTheme {
        RepositoriesFeedLoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoriesFeedSuccessContentPreview(
    @PreviewParameter(GitHubReposPreviewParameterProvider::class)
    gitHubRepositories: List<GitHubRepo>
) {
    MaterialTheme {
        RepositoriesFeedContent(
            isRefreshing = false,
            repos = gitHubRepositories,
            onNavigateToDetails = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoriesFeedEmptyContentPreview() {
    MaterialTheme {
        RepositoriesFeedEmptyContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoriesFeedErrorContentPreview() {
    MaterialTheme {
        RepositoriesFeedErrorContent(onRetry = {})
    }
}
