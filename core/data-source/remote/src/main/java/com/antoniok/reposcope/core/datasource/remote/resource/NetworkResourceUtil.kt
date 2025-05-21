package com.antoniok.reposcope.core.datasource.remote.resource

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal suspend inline fun <reified T> safeApiCall(
    crossinline request: suspend () -> HttpResponse
): NetworkResource<T> = try {
    val response = request()
    if (response.status.isSuccess()) {
        val data = response.body<T>()
        NetworkResource.Success(data)
    } else {
        val errorBody = response.bodyAsText()
        val message = try {
            val json = Json.parseToJsonElement(errorBody).jsonObject
            json["message"]?.jsonPrimitive?.content ?: "Unknown error"
        } catch (_: Exception) {
            "Unknown error"
        }
        NetworkResource.Error(Exception("Error ${response.status.value}: $message"))
    }
} catch (e: Exception) {
    NetworkResource.Error(e)
}
