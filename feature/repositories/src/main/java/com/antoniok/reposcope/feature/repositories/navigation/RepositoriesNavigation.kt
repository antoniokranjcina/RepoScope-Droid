package com.antoniok.reposcope.feature.repositories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.antoniok.reposcope.feature.repositories.screen.repos.RepositoriesScreen
import com.antoniok.reposcope.feature.repositories.screen.details.RepositoryDetailsScreen
import kotlinx.serialization.Serializable

@Serializable data object RepoNavigation

@Serializable data object RepoList
@Serializable data class RepoDetails(val repoId: Long)

fun NavGraphBuilder.repositoriesSection(
    onNavigateToDetails: (id: Long) -> Unit
) {
    navigation<RepoNavigation>(startDestination = RepoList) {
        composable<RepoList> {
            RepositoriesScreen(
                onNavigateToDetails = onNavigateToDetails
            )
        }
        composable<RepoDetails> {
            RepositoryDetailsScreen(
                repoId = it.toRoute<RepoDetails>().repoId
            )
        }
    }
}
