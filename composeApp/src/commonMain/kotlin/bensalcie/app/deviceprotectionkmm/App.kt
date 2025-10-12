package bensalcie.app.deviceprotectionkmm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bensalcie.app.deviceprotectionkmm.devicechecker.DeviceIntegrityChecker
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import deviceprotectionkmm.composeapp.generated.resources.Res
import deviceprotectionkmm.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import kotlin.math.log

@Composable
@Preview
fun App() {

    val checker: DeviceIntegrityChecker = koinInject()
    var text by remember { mutableStateOf("Idle") }
    val scope = rememberCoroutineScope()

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }

            Text(text)
            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                scope.launch {
                    try {
                        val (token, hash) = checker.attest("""{"uid":"demo"}""")
                        text = "Token: ${token?.take(12) ?: "null"}\nHash:$hash"
                        println("Token: ===>  ${token}\nHash:$hash")
                    } catch (e: Exception) {
                        text = "Error: ${e.message}"
                    }
                }
            }) { Text("Check Integrity") }
        }
    }
}