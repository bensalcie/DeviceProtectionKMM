package bensalcie.app.deviceprotectionkmm.devicechecker

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual interface DeviceIntegrityChecker {
    actual suspend fun attest(actionJson: String): Pair<String?, String?>
}