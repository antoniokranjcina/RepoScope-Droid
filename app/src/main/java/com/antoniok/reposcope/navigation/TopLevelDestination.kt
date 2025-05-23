package com.antoniok.reposcope.navigation

import androidx.annotation.StringRes
import com.antoniok.reposcope.R
import com.antoniok.reposcope.feature.repositories.screen.details.RepoDetailsDestination
import com.antoniok.reposcope.feature.repositories.screen.feed.RepositoriesFeedDestination
import com.antoniok.reposcope.feature.repositories.screen.web.WebViewDestination
import kotlin.reflect.KClass

enum class AppDestination(
    @StringRes val titleTextId: Int,
    val isTopLevelDestination: Boolean = false,
    val route: KClass<*>
) {
    FEED(
        titleTextId = R.string.feed_title,
        isTopLevelDestination = true,
        route = RepositoriesFeedDestination::class,
    ),
    FEED_DETAILS(
        titleTextId = R.string.feed_details_title,
        route = RepoDetailsDestination::class,
    ),
    WEB_VIEW(
        titleTextId = R.string.web_view_title,
        route = WebViewDestination::class
    )
}
