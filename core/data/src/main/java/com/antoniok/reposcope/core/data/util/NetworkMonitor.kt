package com.antoniok.reposcope.core.data.util

import kotlinx.coroutines.flow.Flow

/**
 * Utility for reporting app connectivity status
 */
public interface NetworkMonitor {
    public val isOnline: Flow<Boolean>
}
