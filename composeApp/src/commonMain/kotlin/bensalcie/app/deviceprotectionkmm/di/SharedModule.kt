package bensalcie.app.deviceprotectionkmm.di

import bensalcie.app.deviceprotectionkmm.data.remote.repository.IntegrityRepository
import bensalcie.app.deviceprotectionkmm.domain.VerifyDeviceUseCase
import bensalcie.app.deviceprotectionkmm.presentation.DeviceIntegrityViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin Modules Here for Shared Singletons
 */
val sharedModule = module {
    // Put shared singletons here later
    single {
        HttpClient {
            install(ContentNegotiation) {
                // Specify that we want to use Kotlinx JSON for serialization
                json(Json {
                    // Optional: configure serialization properties
                    ignoreUnknownKeys = true // Highly recommended for API clients
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
    single { IntegrityRepository(get()) }
    single { VerifyDeviceUseCase(get(), get()) }
    viewModel<DeviceIntegrityViewModel> { DeviceIntegrityViewModel(get()) }

}

