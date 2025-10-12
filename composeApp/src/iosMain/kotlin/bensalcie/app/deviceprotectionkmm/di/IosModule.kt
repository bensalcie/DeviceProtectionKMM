package bensalcie.app.deviceprotectionkmm.di

import bensalcie.app.deviceprotectionkmm.devicechecker.DeviceIntegrityChecker
import bensalcie.app.deviceprotectionkmm.devicechecker.IosDeviceIntegrityChecker
import org.koin.dsl.module


val iosPlatformModule = module {
    single<DeviceIntegrityChecker> { IosDeviceIntegrityChecker() }
}
