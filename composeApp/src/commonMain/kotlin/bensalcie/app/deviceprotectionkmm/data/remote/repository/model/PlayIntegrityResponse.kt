package bensalcie.app.deviceprotectionkmm.data.remote.repository.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayIntegrityResponse(
    @SerialName("isDeviceValid")
    val isDeviceValid: Boolean?, // true
    @SerialName("raw")
    val raw: Raw?,
    @SerialName("reason")
    val reason: String?, // meets_device_integrity
    @SerialName("status")
    val status: String?, // ok
    @SerialName("timestamp")
    val timestamp: String?, // 2025-10-16T19:50:02+00:00
    @SerialName("verdicts")
    val verdicts: List<String?>?
) {
    @Serializable
    data class Raw(
        @SerialName("accountDetails")
        val accountDetails: AccountDetails?=null,
        @SerialName("appIntegrity")
        val appIntegrity: AppIntegrity?=null,
        @SerialName("deviceIntegrity")
        val deviceIntegrity: DeviceIntegrity?=null,
        @SerialName("requestDetails")
        val requestDetails: RequestDetails?=null
    ) {
        @Serializable
        data class AccountDetails(
            @SerialName("appLicensingVerdict")
            val appLicensingVerdict: String? // UNEVALUATED
        )

        @Serializable
        data class AppIntegrity(
            @SerialName("appRecognitionVerdict")
            val appRecognitionVerdict: String?, // UNRECOGNIZED_VERSION
            @SerialName("certificateSha256Digest")
            val certificateSha256Digest: List<String?>?,
            @SerialName("packageName")
            val packageName: String?, // bensalcie.app.deviceprotectionkmm
            @SerialName("versionCode")
            val versionCode: String? // 1
        )

        @Serializable
        data class DeviceIntegrity(
            @SerialName("deviceRecognitionVerdict")
            val deviceRecognitionVerdict: List<String?>?
        )

        @Serializable
        data class RequestDetails(
            @SerialName("requestHash")
            val requestHash: String?, // myFMRbNpqfwDyHo0w-hRhj-5AXX1RRzPM6lDvVbgjcc=
            @SerialName("requestPackageName")
            val requestPackageName: String?, // bensalcie.app.deviceprotectionkmm
            @SerialName("timestampMillis")
            val timestampMillis: String? // 1760644173564
        )
    }
}