package com.antoniok.reposcope.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberRootScreenAppState(
    navController: NavHostController = rememberNavController(),
): RootScreenAppState = remember(
    navController
) {
    RootScreenAppState(
        navController = navController,
    )
}


@Stable
class RootScreenAppState(
    val navController: NavHostController,
)
