package com.antoniok.reposcope.core.datasource.remote

import com.antoniok.reposcope.core.datasource.remote.api.KtorGitHubNetwork
import com.antoniok.reposcope.core.datasource.remote.model.GitHubRepoDto
import com.antoniok.reposcope.core.datasource.remote.model.OwnerDto
import com.antoniok.reposcope.core.datasource.remote.resource.NetworkResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class KtorGitHubNetworkTest {

    private lateinit var mockEngine: MockEngine
    private lateinit var httpClient: HttpClient
    private lateinit var dataSource: RepoScopeRemoteDataSource

    private val baseUrl = "https://api.github.com"

    @Before
    fun setUp() {
        mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel("[]"),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        dataSource = KtorGitHubNetwork(httpClient, baseUrl)
    }

    @After
    fun tearDown() {
        httpClient.close()
    }

    @Test
    fun `getOrganizationRepos returns Success on valid response`() = runTest {
        val fakeRepos = listOf(
            GitHubRepoDto(
                id = 1,
                name = "Repo1",
                fullName = "square/Repo1",
                htmlUrl = "https://github.com/square/Repo1",
                description = "Test repo",
                language = "Kotlin",
                owner = OwnerDto(
                    id = 1,
                    avatarUrl = "https://github.com/square/avatar",
                    htmlUrl = "https://github.com/square"
                ),
                defaultBranch = "master",
                fork = true,
                disabled = false
            )
        )
        val fakeResponse = Json.encodeToString(ListSerializer(GitHubRepoDto.serializer()), fakeRepos)


        mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(fakeResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        dataSource = KtorGitHubNetwork(httpClient, baseUrl)

        val result = dataSource.getOrganizationRepos("square")
        assertTrue(result is NetworkResource.Success)
        val data = (result as NetworkResource.Success).data
        assertEquals(1, data.size)
        assertEquals("Repo1", data[0].name)
    }

    @Test
    fun `getOrganizationRepos returns Error on 404 response`() = runTest {
        mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(
                    """{"message":"Not Found","documentation_url":"https://docs.github.com/rest"}"""
                ),
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        dataSource = KtorGitHubNetwork(httpClient, baseUrl)

        val result = dataSource.getOrganizationRepos("nonexistent")
        assertTrue(result is NetworkResource.Error)
    }

    @Test
    fun `getOrganizationRepos returns Error on malformed JSON`() = runTest {
        mockEngine = MockEngine {
            respond(
                content = ByteReadChannel("{ invalid json "),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        dataSource = KtorGitHubNetwork(httpClient, baseUrl)

        val result = dataSource.getOrganizationRepos("square")
        assertTrue(result is NetworkResource.Error)
    }

    @Test
    fun `getOrganizationRepos returns Success with empty list`() = runTest {
        mockEngine = MockEngine {
            respond(
                content = ByteReadChannel("[]"),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        dataSource = KtorGitHubNetwork(httpClient, baseUrl)

        val result = dataSource.getOrganizationRepos("square")
        assertTrue(result is NetworkResource.Success)
        assertTrue((result as NetworkResource.Success).data.isEmpty())
    }
}
