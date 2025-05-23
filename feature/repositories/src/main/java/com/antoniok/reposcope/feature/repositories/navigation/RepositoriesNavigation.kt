package com.antoniok.reposcope.feature.repositories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.antoniok.reposcope.feature.repositories.screen.details.repositoryDetailsScreen
import com.antoniok.reposcope.feature.repositories.screen.feed.RepositoriesFeedDestination
import com.antoniok.reposcope.feature.repositories.screen.feed.repositoriesScreen
import com.antoniok.reposcope.feature.repositories.screen.web.webViewScreen
import kotlinx.serialization.Serializable

@Serializable
data object RepoNavigation

fun NavGraphBuilder.repositoriesSection(
    onNavigateToDetails: (id: Long) -> Unit,
    onNavigateToWebView: (url: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    navigation<RepoNavigation>(startDestination = RepositoriesFeedDestination) {
        repositoriesScreen(
            onNavigateToDetails = onNavigateToDetails
        )
        repositoryDetailsScreen(
            onNavigateToWebView = onNavigateToWebView
        )
        webViewScreen(
            onNavigateUp = onNavigateUp
        )
    }
}
