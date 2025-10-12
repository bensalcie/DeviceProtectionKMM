package bensalcie.app.deviceprotectionkmm.di


import bensalcie.app.deviceprotectionkmm.devicechecker.AndroidDeviceChecker
import bensalcie.app.deviceprotectionkmm.devicechecker.DeviceIntegrityChecker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidPlatformModule = module {
    single<DeviceIntegrityChecker> {
        AndroidDeviceChecker(androidContext(), 1073128764436)
    }
}
