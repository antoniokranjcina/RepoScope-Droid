package com.antoniok.reposcope.navigation

import androidx.annotation.StringRes
import com.antoniok.reposcope.R
import com.antoniok.reposcope.feature.repositories.screen.details.RepoDetails
import com.antoniok.reposcope.feature.repositories.screen.feed.RepositoriesFeed
import kotlin.reflect.KClass

enum class AppDestination(
    @StringRes val titleTextId: Int,
    val isTopLevelDestination: Boolean = false,
    val route: KClass<*>
) {
    FEED(
        titleTextId = R.string.feed_title,
        isTopLevelDestination = true,
        route = RepositoriesFeed::class,
    ),
    FEED_DETAILS(
        titleTextId = R.string.feed_details_title,
        route = RepoDetails::class,
    )
}
