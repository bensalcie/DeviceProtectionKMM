package bensalcie.app.deviceprotectionkmm.devicechecker

interface DeviceIntegrityChecker {
    suspend fun attest(actionJson: String): Pair<String?, String?>
}