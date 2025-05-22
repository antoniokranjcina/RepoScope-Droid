package com.antoniok.reposcope.ui.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.antoniok.reposcope.R
import com.antoniok.reposcope.navigation.RootNavHost
import com.antoniok.reposcope.ui.theme.RepoScopeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RootScreen(
    modifier: Modifier = Modifier,
    appState: RootScreenAppState = rememberRootScreenAppState(),
) {
    RepoScopeTheme {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = appState.currentAppDestination?.titleTextId?.let {
                                stringResource(id = it)
                            } ?: ""
                        )
                    },
                    navigationIcon = {
                        if (appState.currentAppDestination?.isTopLevelDestination == false) {
                            IconButton(
                                onClick = { appState.navController.navigateUp() },
                                content = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = stringResource(
                                            id = R.string.back_arrow_content_description
                                        )
                                    )
                                }
                            )
                        }
                    }
                )
            }
        ) {
            RootNavHost(
                appState = appState,
                modifier = Modifier.padding(it)
            )
        }
    }
}
