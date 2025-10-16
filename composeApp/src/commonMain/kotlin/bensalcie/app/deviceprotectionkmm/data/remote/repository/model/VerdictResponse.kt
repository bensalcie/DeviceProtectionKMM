package bensalcie.app.deviceprotectionkmm.data.remote.repository.model

import bensalcie.app.deviceprotectionkmm.domain.DeviceType

data class VerdictResponse(
    val verdict: String,
    val reason: String,
    val verdictType: String,
    val deviceType: DeviceType
)

