package com.antoniok.reposcope.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.antoniok.reposcope.core.model.GitHubRepo
import com.antoniok.reposcope.core.model.Owner

/**
 * A [PreviewParameterProvider] implementation that supplies a list of mock [GitHubRepo] items
 * for use in Jetpack Compose previews.
 *
 * This is used to preview UI components that expect a [List] of [GitHubRepo]s
 * without requiring actual API calls or live data.
 *
 * Example usage:
 * ```
 * @Preview
 * @Composable
 * fun RepositoriesContentPreview(
 *     @PreviewParameter(GitHubPreviewParameterProvider::class) repos: List<GitHubRepo>
 * ) {
 *     RepositoriesContent(
 *         uiState = RepositoriesUiState.Success(repos),
 *         onNavigateToDetails = {}
 *     )
 * }
 * ```
 */
public class GitHubPreviewParameterProvider : PreviewParameterProvider<List<GitHubRepo>> {
    override val values: Sequence<List<GitHubRepo>>
        get() = sequenceOf(
            listOf(
                GitHubRepo(
                    id = 274562,
                    name = "yajl-objc",
                    fullName = "square/yajl-objc",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/yajl-objc",
                    description = "Objective-C bindings for YAJL (Yet Another JSON Library) C library",
                    language = null
                ),
                GitHubRepo(
                    id = 123456,
                    name = "gradle-dependencies-sorter",
                    fullName = "square/gradle-dependencies-sorter",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/gradle-dependencies-sorter",
                    description = "A CLI app and Gradle plugin to sort the dependencies in your Gradle build scripts",
                    language = "Kotlin"
                ),
                GitHubRepo(
                    id = 234567,
                    name = "okhttp-icu",
                    fullName = "square/okhttp-icu",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/okhttp-icu",
                    description = "Builds a minimal subset of ICU required by OkHttp.",
                    language = "Kotlin"
                ),
                GitHubRepo(
                    id = 345678,
                    name = "mobile-payments-sdk-react-native",
                    fullName = "square/mobile-payments-sdk-react-native",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/mobile-payments-sdk-react-native",
                    description = "Mobile Payments SDK React Native Plug-in",
                    language = "TypeScript"
                ),
                GitHubRepo(
                    id = 456789,
                    name = "square-mcp-server",
                    fullName = "square/square-mcp-server",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/square-mcp-server",
                    description = "A Model Context Protocol (MCP) server for square",
                    language = "TypeScript"
                ),
                GitHubRepo(
                    id = 567890,
                    name = "mobile-payments-sdk-ios",
                    fullName = "square/mobile-payments-sdk-ios",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/mobile-payments-sdk-ios",
                    description = "Public repository that hosts the Square Mobile Payments SDK iOS binaries",
                    language = "Swift"
                ),
                GitHubRepo(
                    id = 678901,
                    name = "site-theme-sdk",
                    fullName = "square/site-theme-sdk",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/site-theme-sdk",
                    description = "A lightweight, open source JavaScript SDK that wraps basic asynchronous functionality of Custom Site client APIs as well as additional helper functionality.",
                    language = "TypeScript"
                ),
                GitHubRepo(
                    id = 789012,
                    name = "luks2crypt",
                    fullName = "square/luks2crypt",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/luks2crypt",
                    description = "Manage linux luks client devices and escrow recovery keys to crypt-server",
                    language = "Go"
                ),
                GitHubRepo(
                    id = 890123,
                    name = "square-python-sdk",
                    fullName = "square/square-python-sdk",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/square-python-sdk",
                    description = "Python client library for the Square API",
                    language = "Python"
                ),
                GitHubRepo(
                    id = 901234,
                    name = "certigo",
                    fullName = "square/certigo",
                    owner = Owner(
                        id = 82592,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                        htmlUrl = "https://github.com/square"
                    ),
                    htmlUrl = "https://github.com/square/certigo",
                    description = "A utility to examine and validate certificates in a variety of formats",
                    language = "Go"
                )
            )
        )
}
