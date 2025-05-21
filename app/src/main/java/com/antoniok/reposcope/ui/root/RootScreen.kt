package com.antoniok.reposcope.ui.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.antoniok.reposcope.navigation.RootNavHost
import com.antoniok.reposcope.ui.theme.RepoScopeTheme

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
    appState: RootScreenAppState = rememberRootScreenAppState(),
) {
    RepoScopeTheme {
        Scaffold(
            modifier = modifier,
        ) {
            RootNavHost(
                appState = appState,
                modifier = Modifier.padding(it)
            )
        }
    }
}