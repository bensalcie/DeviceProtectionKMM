package bensalcie.app.deviceprotectionkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform