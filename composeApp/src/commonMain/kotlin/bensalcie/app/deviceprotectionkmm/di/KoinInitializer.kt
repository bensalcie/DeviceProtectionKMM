package bensalcie.app.deviceprotectionkmm.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.core.KoinApplication

// Declare this expect so we can load platform-specific modules dynamically
expect fun platformModule(): org.koin.core.module.Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    // Stop any existing instance (useful on iOS when reinitializing)
    stopKoin()

    return startKoin {
        appDeclaration()
        modules(
            listOf(
                sharedModule,
                platformModule() // adds Android or iOS module automatically
            )
        )
    }
}
