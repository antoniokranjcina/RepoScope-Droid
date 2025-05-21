package com.antoniok.reposcope.feature.repositories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.antoniok.reposcope.feature.repositories.screen.details.repositoryDetailsScreen
import com.antoniok.reposcope.feature.repositories.screen.feed.RepositoriesFeed
import com.antoniok.reposcope.feature.repositories.screen.feed.repositoriesScreen
import kotlinx.serialization.Serializable

@Serializable
data object RepoNavigation

fun NavGraphBuilder.repositoriesSection(
    onNavigateToDetails: (id: Long) -> Unit
) {
    navigation<RepoNavigation>(startDestination = RepositoriesFeed) {
        repositoriesScreen(
            onNavigateToDetails = onNavigateToDetails
        )
        repositoryDetailsScreen()
    }
}
