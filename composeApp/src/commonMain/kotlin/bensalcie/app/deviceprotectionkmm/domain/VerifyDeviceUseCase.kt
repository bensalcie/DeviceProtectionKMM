package bensalcie.app.deviceprotectionkmm.domain

import bensalcie.app.deviceprotectionkmm.data.remote.repository.IntegrityRepository
import bensalcie.app.deviceprotectionkmm.data.remote.repository.IntegrityResult
import bensalcie.app.deviceprotectionkmm.data.remote.repository.model.VerdictResponse
import bensalcie.app.deviceprotectionkmm.devicechecker.DeviceIntegrityChecker


class VerifyDeviceUseCase(
    private val checker: DeviceIntegrityChecker,
    private val repo: IntegrityRepository
) {
    suspend operator fun invoke(actionJson: String): DeviceVerificationState {
        val (token, hash) = checker.attest(actionJson)

        val deviceType =
            DeviceType.ANDROID.takeIf { token != null } ?: DeviceType.IOS
        return when (deviceType) {
            DeviceType.IOS -> DeviceVerificationState.Valid(
                VerdictResponse(
                    verdict = hash ?: "",
                    reason = hash ?: "",
                    verdictType = hash ?: "",
                    deviceType = deviceType
                )
            )

            DeviceType.ANDROID -> when (val result = repo.verifyToken(token ?: "")) {
                is IntegrityResult.Success -> DeviceVerificationState.Valid(result.verificationResponse)
                is IntegrityResult.Error -> DeviceVerificationState.Invalid(result.message)
            }
        }

    }
}

sealed class DeviceVerificationState {
    data class Valid(val response: VerdictResponse) : DeviceVerificationState()
    data class Invalid(val reason: String) : DeviceVerificationState()
}


enum class DeviceType {
    IOS,
    ANDROID
}