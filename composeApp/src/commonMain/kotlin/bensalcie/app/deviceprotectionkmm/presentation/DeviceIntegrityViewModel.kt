package bensalcie.app.deviceprotectionkmm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bensalcie.app.deviceprotectionkmm.domain.model.DeviceVerificationState
import bensalcie.app.deviceprotectionkmm.domain.model.VerifyDeviceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeviceIntegrityViewModel(
    private val verifyDeviceUseCase: VerifyDeviceUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<DeviceVerificationState?>(null)
    val state: StateFlow<DeviceVerificationState?> = _state

    fun verify() {
        viewModelScope.launch {
            _state.value = verifyDeviceUseCase("{\"action\":\"verify_device\"}")
        }
    }
}
