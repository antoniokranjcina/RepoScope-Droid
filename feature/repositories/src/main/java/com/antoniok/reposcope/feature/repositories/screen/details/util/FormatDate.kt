package com.antoniok.reposcope.feature.repositories.screen.details.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


internal fun String?.formatDate(): String = try {
    val instant = Instant.parse(this)
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withZone(ZoneId.systemDefault())
    formatter.format(instant)
} catch (e: Exception) {
    this ?: ""
}
