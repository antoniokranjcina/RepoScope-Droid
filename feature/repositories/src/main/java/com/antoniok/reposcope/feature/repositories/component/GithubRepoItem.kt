package com.antoniok.reposcope.feature.repositories.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.model.Owner

@Composable
internal fun GithubRepoItem(
    repo: GitHubRepo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = repo.description ?: "No description",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun GithubRepoItemPreview() {
    GithubRepoItem(
        repo = GitHubRepo(
            id = 0,
            name = "NowInAndroid",
            fullName = "android/NowInAndroid",
            owner = Owner(
                id = 1,
                avatarUrl = "www.image.com",
                htmlUrl = "www.github.com/android"
            ),
            htmlUrl = "www.github.com/android",
            description = "Cool Repo",
            language = "Kotlin",
        ),
        onClick = {}
    )
}