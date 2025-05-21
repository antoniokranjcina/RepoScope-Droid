package com.antoniok.reposcope.feature.repositories

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RepositoriesScreen(
    onNavigateToDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    RepositoriesContent(
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@Composable
private fun RepositoriesContent(
    onNavigateToDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Repositories Screen")
        Button(
            onClick = { onNavigateToDetails(123L) },
            content = { Text("Go to Details") }
        )
    }
}

@Preview
@Composable
private fun RepositoriesContentPreview() {
    RepositoriesContent(onNavigateToDetails = {})
}
