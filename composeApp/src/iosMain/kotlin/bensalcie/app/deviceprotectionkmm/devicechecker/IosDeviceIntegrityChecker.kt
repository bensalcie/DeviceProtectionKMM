package bensalcie.app.deviceprotectionkmm.devicechecker

import platform.Foundation.NSFileManager
import platform.Foundation.NSTemporaryDirectory

class IosDeviceIntegrityChecker: DeviceIntegrityChecker {
    override suspend fun attest(actionJson: String): Pair<String?, String> {
        val jailbroken = checkJailbreak()
        val hash = actionJson.hashCode().toString() // stub: implement SHA256-base64url same as Android
        return (if (jailbroken) null else null) to hash
    }
    private fun checkJailbreak(): Boolean {
        val suspiciousPaths = listOf(
            "/Applications/Cydia.app",
            "/Library/MobileSubstrate/MobileSubstrate.dylib",
            "/bin/bash"
        )
        val fm = NSFileManager.defaultManager
        return suspiciousPaths.any { fm.fileExistsAtPath(it) } ||
                fm.isWritableFileAtPath(NSTemporaryDirectory())
    }
}