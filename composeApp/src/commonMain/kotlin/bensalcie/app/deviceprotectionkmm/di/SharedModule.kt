package bensalcie.app.deviceprotectionkmm.di

import bensalcie.app.deviceprotectionkmm.data.remote.repository.IntegrityRepository
import bensalcie.app.deviceprotectionkmm.domain.model.VerifyDeviceUseCase
import bensalcie.app.deviceprotectionkmm.presentation.DeviceIntegrityViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin Modules Here for Shared Singletons
 */
val sharedModule = module {
    // Put shared singletons here later
    single { HttpClient() }
    single { IntegrityRepository(get()) }
    single { VerifyDeviceUseCase(get(), get()) }
    viewModel<DeviceIntegrityViewModel> { DeviceIntegrityViewModel(get()) }

}