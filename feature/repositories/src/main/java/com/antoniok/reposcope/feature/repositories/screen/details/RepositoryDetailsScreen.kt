package com.antoniok.reposcope.feature.repositories.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun RepositoryDetailsScreen(
    repoId: Long,
    modifier: Modifier = Modifier
) {
    RepositoryDetailsContent(
        repoId = repoId,
        modifier = modifier
    )
}

@Composable
private fun RepositoryDetailsContent(
    repoId: Long,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("Repository Details Screen: $repoId")
    }
}

@Preview
@Composable
private fun RepositoryDetailsContentPreview() {
    RepositoryDetailsContent(
        repoId = 123L
    )
}
