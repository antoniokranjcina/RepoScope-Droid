package com.antoniok.reposcope.feature.repositories.screen.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antoniok.reposcope.feature.repositories.R

@Composable
internal fun RepoStatsRow(
    watchers: Int,
    stars: Int,
    forks: Int,
    openIssues: Int,
    hasIssues: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatItem(
                icon = Icons.Default.Visibility,
                label = stringResource(R.string.repo_details_watchers),
                value = watchers,
                modifier = Modifier.weight(1f)
            )
            StatItem(
                icon = Icons.Default.Star,
                label = stringResource(R.string.repo_details_stars),
                value = stars,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatItem(
                icon = Icons.Default.Share,
                label = stringResource(R.string.repo_details_forks),
                value = forks,
                modifier = Modifier.weight(1f)
            )
            StatItem(
                icon = Icons.Default.BugReport,
                label = stringResource(R.string.repo_details_open_issues),
                value = if (hasIssues) openIssues else 0,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepoStatsRowPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            RepoStatsRow(
                watchers = 124,
                stars = 4321,
                forks = 789,
                openIssues = 32,
                hasIssues = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
