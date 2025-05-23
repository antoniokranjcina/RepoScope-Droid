package com.antoniok.reposcope.feature.repositories.screen.web

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class WebViewDestination(val url: String)

internal fun NavGraphBuilder.webViewScreen(
    onNavigateUp: () -> Unit
) {
    composable<WebViewDestination> { backStackEntry ->
        val url = backStackEntry.toRoute<WebViewDestination>().url
        WebViewScreen(
            url = url,
            onNavigateUp = onNavigateUp
        )
    }
}

@Composable
fun WebViewScreen(
    url: String,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    // Handle system back press
    BackHandler(enabled = true) {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onNavigateUp()
        }
    }

    AndroidView(
        factory = {
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(url)
                settings.javaScriptEnabled = true
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
