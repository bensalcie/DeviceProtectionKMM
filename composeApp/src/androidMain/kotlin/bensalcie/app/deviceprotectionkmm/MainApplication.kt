package bensalcie.app.deviceprotectionkmm

import android.app.Application
import bensalcie.app.deviceprotectionkmm.di.androidPlatformModule
import bensalcie.app.deviceprotectionkmm.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(androidPlatformModule, sharedModule)
        }
    }
}