package bensalcie.app.deviceprotectionkmm.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(iosPlatformModule, sharedModule)
    }
}
