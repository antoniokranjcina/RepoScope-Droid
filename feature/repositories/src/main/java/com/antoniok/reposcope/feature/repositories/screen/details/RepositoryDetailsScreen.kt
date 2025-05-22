package com.antoniok.reposcope.feature.repositories.screen.details

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.ui.GitHubRepoPreviewParameterProvider
import com.antoniok.reposcope.feature.repositories.R
import com.antoniok.reposcope.feature.repositories.screen.component.ErrorScreenContent
import com.antoniok.reposcope.feature.repositories.screen.details.component.DateItem
import com.antoniok.reposcope.feature.repositories.screen.details.component.InfoLabelValue
import com.antoniok.reposcope.feature.repositories.screen.details.component.RepoStatsRow
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Serializable
data class RepoDetails(val repoId: Long)

internal fun NavGraphBuilder.repositoryDetailsScreen() {
    composable<RepoDetails> {
        RepositoryDetailsScreen(
            repoId = it.toRoute<RepoDetails>().repoId
        )
    }
}

@Composable
internal fun RepositoryDetailsScreen(
    repoId: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: RepositoryDetailViewModel = koinViewModel { parametersOf(repoId) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RepositoryDetailsContent(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
private fun RepositoryDetailsContent(
    uiState: DetailUiState,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = uiState,
        label = "details-ui-state"
    ) { state ->
        when (state) {
            is DetailUiState.Success -> RepositoryDetailsSuccessContent(
                repo = state.repo,
                modifier = modifier
            )

            is DetailUiState.Error -> RepositoryDetailsErrorContent(modifier = modifier)
            DetailUiState.Loading -> RepositoryDetailsLoadingContent(modifier = modifier)
        }
    }
}

@Composable
private fun RepositoryDetailsLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun RepositoryDetailsSuccessContent(
    repo: GitHubRepo,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = repo.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DateItem(
                label = stringResource(R.string.repo_details_created_at),
                date = repo.createdAt.formatDate()
            )
            DateItem(
                label = stringResource(R.string.repo_details_updated_at),
                date = repo.updatedAt.formatDate()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        RepoStatsRow(
            watchers = repo.watchersCount,
            stars = repo.stargazersCount,
            forks = repo.forksCount,
            openIssues = repo.openIssuesCount,
            hasIssues = repo.hasIssues,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_full_name_label),
            value = repo.fullName
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_owner_label),
            value = "",
            customContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(repo.owner.avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.repo_details_owner_label),
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, repo.owner.htmlUrl.toUri())
                            context.startActivity(intent)
                        },
                        content = {
                            Text(text = stringResource(R.string.repo_details_view_owner_profile))
                        }
                    )
                }
            }
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_language_label),
            value = repo.language ?: stringResource(R.string.repo_details_unknown_language)
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_description_label),
            value = repo.description ?: stringResource(R.string.repo_details_no_description)
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_homepage_label),
            value = repo.homepage?.takeIf { it.isNotBlank() }
                ?: stringResource(R.string.repo_details_no_homepage)
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_default_branch_label),
            value = repo.defaultBranch
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_forked_label),
            value = if (repo.fork) stringResource(R.string.yes) else stringResource(R.string.no)
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_archived_label),
            value = if (repo.archived) stringResource(R.string.yes) else stringResource(R.string.no)
        )

        InfoLabelValue(
            label = stringResource(R.string.repo_details_disabled_label),
            value = if (repo.disabled) stringResource(R.string.yes) else stringResource(R.string.no)
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, repo.htmlUrl.toUri())
                context.startActivity(intent)
            },
            content = { Text(text = stringResource(R.string.repo_details_view_on_github)) }
        )
    }
}

@Composable
private fun RepositoryDetailsErrorContent(
    modifier: Modifier = Modifier
) {
    ErrorScreenContent(
        subtitle = stringResource(id = R.string.repo_details_error_subtitle),
        modifier = modifier
    )
}


// TODO probably should save instant to DB
fun String?.formatDate(): String = try {
    val instant = Instant.parse(this)
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withZone(ZoneId.systemDefault())
    formatter.format(instant)
} catch (e: Exception) {
    this ?: ""
}


@Preview(showBackground = true)
@Composable
private fun RepositoryDetailsContentLoadingPreview() {
    MaterialTheme {
        RepositoryDetailsLoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryDetailsContentSuccessPreview(
    @PreviewParameter(GitHubRepoPreviewParameterProvider::class)
    gitHubRepo: GitHubRepo
) {
    MaterialTheme {
        RepositoryDetailsSuccessContent(
            repo = gitHubRepo
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryDetailsContentErrorPreview() {
    MaterialTheme {
        RepositoryDetailsErrorContent()
    }
}
