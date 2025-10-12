package bensalcie.app.deviceprotectionkmm.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(platformModule: Module) {
    startKoin {
        modules(platformModule, sharedModule)
    }
}