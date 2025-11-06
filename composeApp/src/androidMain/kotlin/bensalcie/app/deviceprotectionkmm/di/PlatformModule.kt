package bensalcie.app.deviceprotectionkmm.di


import bensalcie.app.deviceprotectionkmm.devicechecker.AndroidDeviceChecker
import bensalcie.app.deviceprotectionkmm.devicechecker.DeviceIntegrityChecker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidPlatformModule = module {
    single<DeviceIntegrityChecker> {
        AndroidDeviceChecker(androidContext(), 11111111111111)// Add your own Cloud Console Key
    }
}

actual fun platformModule() = androidPlatformModule
