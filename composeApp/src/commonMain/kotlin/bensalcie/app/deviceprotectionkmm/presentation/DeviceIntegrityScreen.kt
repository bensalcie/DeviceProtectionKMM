package bensalcie.app.deviceprotectionkmm.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bensalcie.app.deviceprotectionkmm.domain.model.DeviceVerificationState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeviceIntegrityScreen(viewModel: DeviceIntegrityViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Device Integrity Verification", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(20.dp))
            Button(onClick = { viewModel.verify() }) {
                Text("Verify Device")
            }

            Spacer(Modifier.height(20.dp))
            when (val s = state) {
                is DeviceVerificationState.Valid ->
                    Text("✅ Verified: ${s.message .take(120)}", color = MaterialTheme.colorScheme.primary)
                is DeviceVerificationState.Invalid ->
                    Text("❌ Invalid: ${s.reason}", color = MaterialTheme.colorScheme.error)
                null -> Text("Press 'Verify Device' to start.")
            }
        }
    }
}
