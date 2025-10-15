package bensalcie.app.deviceprotectionkmm.data.remote.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess


class IntegrityRepository(private val httpClient: HttpClient) {

    suspend fun verifyToken(token: String): IntegrityResult {
        return try {
            val response: HttpResponse = httpClient.post("https://android.bensalcie.space/playintegrity/verify_integrity.php") {
                contentType(ContentType.Application.Json)
                setBody("""{"token":"$token"}""")
            }

            if (response.status.isSuccess()) {
                val text = response.bodyAsText()
                IntegrityResult.Success(text)
            } else {
                IntegrityResult.Error("HTTP ${response.status.value}: ${response.bodyAsText()}")
            }
        } catch (e: Exception) {
            IntegrityResult.Error(e.message ?: "Unknown error")
        }
    }
}

sealed class IntegrityResult {
    data class Success(val rawJson: String) : IntegrityResult()
    data class Error(val message: String) : IntegrityResult()
}
