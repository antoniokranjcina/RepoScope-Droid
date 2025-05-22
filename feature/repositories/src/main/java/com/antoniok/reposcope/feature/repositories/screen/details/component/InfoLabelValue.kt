package com.antoniok.reposcope.feature.repositories.screen.details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
        customContent?.let {
            it()
        } ?: run {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoLabelValuePreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoLabelValue(
                label = "Owner",
                value = "square"
            )
            Spacer(modifier = Modifier.height(12.dp))
            InfoLabelValue(
                label = "Homepage",
                value = "",
                customContent = {
                    Text(
                        text = "https://squareup.com",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    }
}
