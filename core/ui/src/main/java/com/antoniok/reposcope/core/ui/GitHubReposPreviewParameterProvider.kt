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
 *
 * @see androidx.compose.ui.tooling.preview.PreviewParameter
 */
public class GitHubReposPreviewParameterProvider : PreviewParameterProvider<List<GitHubRepo>> {
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
                    language = null,
                    homepage = null,
                    defaultBranch = "master",
                    fork = false,
                    disabled = false,
                    watchersCount = 123,
                    forksCount = 45,
                    stargazersCount = 678,
                    hasIssues = true,
                    openIssuesCount = 12,
                    archived = false,
                    createdAt = "2011-01-01T12:00:00Z",
                    updatedAt = "2023-01-01T12:00:00Z"
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
                    language = "Kotlin",
                    homepage = "https://square.github.io/gradle-dependencies-sorter",
                    defaultBranch = "main",
                    fork = false,
                    disabled = false,
                    watchersCount = 56,
                    forksCount = 30,
                    stargazersCount = 240,
                    hasIssues = true,
                    openIssuesCount = 4,
                    archived = false,
                    createdAt = "2022-05-12T10:00:00Z",
                    updatedAt = "2024-05-10T08:00:00Z"
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
                    language = "Kotlin",
                    homepage = null,
                    defaultBranch = "main",
                    fork = true,
                    disabled = false,
                    watchersCount = 100,
                    forksCount = 10,
                    stargazersCount = 80,
                    hasIssues = false,
                    openIssuesCount = 0,
                    archived = false,
                    createdAt = "2020-11-20T12:30:00Z",
                    updatedAt = "2024-03-18T09:45:00Z"
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
                    language = "TypeScript",
                    homepage = null,
                    defaultBranch = "develop",
                    fork = false,
                    disabled = false,
                    watchersCount = 25,
                    forksCount = 12,
                    stargazersCount = 75,
                    hasIssues = true,
                    openIssuesCount = 3,
                    archived = false,
                    createdAt = "2021-07-01T09:00:00Z",
                    updatedAt = "2023-10-22T14:00:00Z"
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
                    language = "TypeScript",
                    homepage = null,
                    defaultBranch = "main",
                    fork = false,
                    disabled = true,
                    watchersCount = 12,
                    forksCount = 6,
                    stargazersCount = 50,
                    hasIssues = false,
                    openIssuesCount = 0,
                    archived = true,
                    createdAt = "2021-03-15T11:00:00Z",
                    updatedAt = "2024-01-01T12:00:00Z"
                )
                // Add similar updates to other items if needed
            )
        )
}

/**
 * A [PreviewParameterProvider] that supplies a single mock [GitHubRepo] instance
 * for use in Jetpack Compose previews.
 *
 * This allows UI components that require a [GitHubRepo] to be previewed in isolation
 * without relying on live data or network responses.
 *
 * Example usage:
 * ```
 * @Preview
 * @Composable
 * fun GitHubRepoDetailPreview(
 *     @PreviewParameter(GitHubRepoPreviewParameterProvider::class) repo: GitHubRepo
 * ) {
 *     GitHubRepoDetailScreen(repo = repo)
 * }
 * ```
 *
 * @see androidx.compose.ui.tooling.preview.PreviewParameter
 */
public class GitHubRepoPreviewParameterProvider : PreviewParameterProvider<GitHubRepo> {
    override val values: Sequence<GitHubRepo>
        get() = sequenceOf(
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
                language = null,
                homepage = null,
                defaultBranch = "master",
                fork = false,
                disabled = false,
                watchersCount = 123,
                forksCount = 45,
                stargazersCount = 678,
                hasIssues = true,
                openIssuesCount = 12,
                archived = false,
                createdAt = "2011-01-01T12:00:00Z",
                updatedAt = "2023-01-01T12:00:00Z"
            )
        )
}
