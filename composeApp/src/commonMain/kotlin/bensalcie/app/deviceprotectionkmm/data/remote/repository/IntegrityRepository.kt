package bensalcie.app.deviceprotectionkmm.data.remote.repository

import bensalcie.app.deviceprotectionkmm.data.remote.repository.model.PlayIntegrityResponse
import bensalcie.app.deviceprotectionkmm.data.remote.repository.model.VerdictResponse
import bensalcie.app.deviceprotectionkmm.domain.DeviceType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess


class IntegrityRepository(private val httpClient: HttpClient) {

    suspend fun verifyToken(token: String): IntegrityResult {
        return try {
            val response: HttpResponse =
                httpClient.post("https://android.bensalcie.space/playintegrity/verify_integrity.php") {
                    contentType(ContentType.Application.Json)
                    setBody(mapOf("token" to token))

                }

            if (response.status.isSuccess()) {
                val playIntegrityResponse: PlayIntegrityResponse = response.body()


                if (playIntegrityResponse.isDeviceValid == true)
                    IntegrityResult.Success(
                        VerdictResponse(
                            verdict = playIntegrityResponse.verdicts?.firstOrNull() ?: "",
                            verdictType = playIntegrityResponse.raw?.deviceIntegrity?.deviceRecognitionVerdict?.firstOrNull()
                                ?: "",
                            reason = playIntegrityResponse.reason ?: "",
                            deviceType = DeviceType.ANDROID
                        )
                    ) else IntegrityResult.Error("Device is not valid Status: ${playIntegrityResponse.raw?.appIntegrity?.appRecognitionVerdict ?: ""}")
            } else {
                IntegrityResult.Error("Device is not valid")
            }
        } catch (e: Exception) {
            IntegrityResult.Error("Device is not valid")
        }
    }
}

sealed class IntegrityResult {
    data class Success(val verificationResponse: VerdictResponse) : IntegrityResult()
    data class Error(val message: String) : IntegrityResult()
}
