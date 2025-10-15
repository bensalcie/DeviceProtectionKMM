package bensalcie.app.deviceprotectionkmm.domain.model

import bensalcie.app.deviceprotectionkmm.data.remote.repository.IntegrityRepository
import bensalcie.app.deviceprotectionkmm.data.remote.repository.IntegrityResult
import bensalcie.app.deviceprotectionkmm.devicechecker.DeviceIntegrityChecker


class VerifyDeviceUseCase(
    private val checker: DeviceIntegrityChecker,
    private val repo: IntegrityRepository
) {
    suspend operator fun invoke(actionJson: String): DeviceVerificationState {
        val (token, hash) = checker.attest(actionJson)

        return if (token == null) {
            DeviceVerificationState.Invalid("No integrity token generated (possible jailbreak or emulator)")
        } else {
            when (val result = repo.verifyToken(token)) {
                is IntegrityResult.Success -> DeviceVerificationState.Valid(result.rawJson)
                is IntegrityResult.Error -> DeviceVerificationState.Invalid(result.message)
            }
        }
    }
}

sealed class DeviceVerificationState {
    data class Valid(val message: String) : DeviceVerificationState()
    data class Invalid(val reason: String) : DeviceVerificationState()
}
