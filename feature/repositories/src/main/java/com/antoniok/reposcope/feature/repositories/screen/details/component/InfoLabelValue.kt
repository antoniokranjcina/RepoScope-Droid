package com.antoniok.reposcope.feature.repositories.screen.details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun InfoLabelValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    customContent: (@Composable () -> Unit)? = null
) {
    Column(modifier = modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        if (customContent != null) {
            customContent()
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
