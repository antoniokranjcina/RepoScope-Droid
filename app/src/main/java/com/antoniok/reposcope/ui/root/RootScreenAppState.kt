package com.antoniok.reposcope.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.antoniok.reposcope.navigation.AppDestination

@Composable
internal fun rememberRootScreenAppState(
    navController: NavHostController = rememberNavController(),
): RootScreenAppState = remember(
    navController
) {
    RootScreenAppState(
        navController = navController,
    )
}


@Stable
internal class RootScreenAppState(
    val navController: NavHostController,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    private val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentAppDestination: AppDestination?
        @Composable get() {
            return AppDestination.entries.firstOrNull { dest ->
                currentDestination?.hasRoute(route = dest.route) == true
            }
        }
}
