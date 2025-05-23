package com.antoniok.reposcope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.antoniok.reposcope.core.data.util.NetworkMonitor
import com.antoniok.reposcope.ui.root.RootScreen
import com.antoniok.reposcope.ui.root.rememberRootScreenAppState
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val appState = rememberRootScreenAppState(
                networkMonitor = networkMonitor,
            )

            RootScreen(
                appState = appState
            )
        }
    }
}
