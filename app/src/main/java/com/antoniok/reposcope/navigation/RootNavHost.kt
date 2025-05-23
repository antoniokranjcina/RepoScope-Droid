package com.antoniok.reposcope.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.antoniok.reposcope.feature.repositories.navigation.RepoNavigation
import com.antoniok.reposcope.feature.repositories.navigation.repositoriesSection
import com.antoniok.reposcope.feature.repositories.screen.details.RepoDetailsDestination
import com.antoniok.reposcope.feature.repositories.screen.web.WebViewDestination
import com.antoniok.reposcope.ui.root.RootScreenAppState

@Composable
internal fun RootNavHost(
    appState: RootScreenAppState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = appState.navController,
        startDestination = RepoNavigation,
        modifier = modifier
    ) {
        repositoriesSection(
            onNavigateToDetails = { repoId ->
                appState.navController.navigate(
                    RepoDetailsDestination(repoId = repoId)
                )
            },
            onNavigateToWebView = { url ->
                appState.navController.navigate(
                    WebViewDestination(url = url)
                )
            },
            onNavigateUp = {
                appState.navController.navigateUp()
            }
        )
    }
}
