package bensalcie.app.deviceprotectionkmm.devicechecker

import android.content.Context
import android.util.Base64
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.StandardIntegrityManager
import kotlinx.coroutines.suspendCancellableCoroutine
import java.security.MessageDigest
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidDeviceChecker(
    private val context: Context,
    private val cloudProjectNumber: Long
) : DeviceIntegrityChecker {
    private val mgr by lazy { IntegrityManagerFactory.createStandard(context) }

    override suspend fun attest(actionJson: String): Pair<String?, String> {
        val hash = sha256Base64Url(actionJson)
        val provider = prepareProvider()
        val token = requestToken(provider, hash)
        return token to hash
    }

    private fun sha256Base64Url(payload: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(payload.toByteArray())
        return Base64.encodeToString(digest, Base64.NO_WRAP or Base64.URL_SAFE)
    }

    private suspend fun prepareProvider() =
        suspendCancellableCoroutine<StandardIntegrityManager.StandardIntegrityTokenProvider> { cont ->
            val req = StandardIntegrityManager.PrepareIntegrityTokenRequest.builder()
                .setCloudProjectNumber(cloudProjectNumber)
                .build()
            mgr.prepareIntegrityToken(req)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    private suspend fun requestToken(
        provider: StandardIntegrityManager.StandardIntegrityTokenProvider,
        hash: String
    ) = suspendCancellableCoroutine<String> { cont ->
        val req = StandardIntegrityManager.StandardIntegrityTokenRequest.builder()
            .setRequestHash(hash)
            .build()
        provider.request(req)
            .addOnSuccessListener { cont.resume(it.token()) }
            .addOnFailureListener { cont.resumeWithException(it) }
    }
}

