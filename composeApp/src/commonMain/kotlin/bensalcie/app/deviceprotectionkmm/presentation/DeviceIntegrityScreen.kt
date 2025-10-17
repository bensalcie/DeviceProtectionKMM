package bensalcie.app.deviceprotectionkmm.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bensalcie.app.deviceprotectionkmm.domain.DeviceVerificationState
import org.koin.compose.viewmodel.koinViewModel

val PrimaryYellow = Color(0xFFFFC300)
val SecondaryOrange = Color(0xFFFF9900)
val DarkBlue = Color(0xFF192A56) // Used for phone outline and primary text


@Composable
fun DeviceIntegrityScreen(viewModel: DeviceIntegrityViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    val gradientColors = listOf(SecondaryOrange, PrimaryYellow)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Background is white outside the phone area
    ) {
        // --- 1. Top Yellow/Orange Background (Matches the image's overall feel) ---
        // Fills the top portion with a smooth transition
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f) // Adjust height as needed
                .background(
                    Brush.verticalGradient(
                        listOf(PrimaryYellow, SecondaryOrange)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // This Box represents the 'screen' part of the phone UI from the image
            Box(
                modifier = Modifier
                    .width(300.dp) // Fixed width for phone simulation
                    .height(550.dp) // Fixed height for phone simulation
                    .clip(RoundedCornerShape(32.dp)) // Rounded corners for phone screen
                    .background(Color.White) // Screen background
            ) {
                // The actual content is stacked inside the simulated phone screen
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- Top Gradient Section (The orange/yellow splash) ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .background(
                                Brush.verticalGradient(gradientColors)
                            )
                            .padding(top = 40.dp, bottom = 20.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        // 'AG' Logo
                        Text(
                            text = "DroidconKE",
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            ),
                        )
                    }

                    // --- Bottom Content Section (White background) ---
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Text Content (Welcome message)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Welcome",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "In the world of beautiful\nGraphic Designs",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Gray
                                ),
                                textAlign = TextAlign.Center
                            )
                        }


                        // Button & State
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // State Output (Kept from original code, but styled)
                            when (val s = state) {
                                is DeviceVerificationState.Valid -> {
                                    LaunchedEffect(Unit) {
                                        isSuccess = true
                                        showDialog = true
                                    }
                                    Text(
                                        text = "✅ Verified: ${s.response.verdict}",
                                        color = Color(0xFF4CAF50), // Green for success
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                is DeviceVerificationState.Invalid -> {
                                    LaunchedEffect(Unit) {
                                        isSuccess = false
                                        showDialog = true
                                    }
                                    Text(
                                        text = "❌ ${s.reason}",
                                        color = Color(0xFFE53935), // Red for failure
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                is DeviceVerificationState.Loading -> {
                                    CircularProgressIndicator(
                                        color = SecondaryOrange,
                                        strokeWidth = 3.dp
                                    )
                                }

                                null -> Text(
                                    text = "Press 'GET STARTED' to start.",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(Modifier.height(32.dp))

                            // 'Get Started' Button (Styled to match the image)
                            Button(
                                onClick = { viewModel.verify() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(25.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SecondaryOrange),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                            ) {
                                Text(
                                    "GET STARTED",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White // Text color for the button in the image
                                )
                            }
                        }
                    }
                }

                // Phone Notch (Optional realism)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-10).dp)
                        .size(width = 100.dp, height = 20.dp)
                        .background(
                            DarkBlue,
                            RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                        )
                )
            }
        }

        if (showDialog) {
            AnimatedVisibility(
                visible = showDialog,
                enter = fadeIn() + scaleIn(initialScale = 0.8f),
                exit = fadeOut() + scaleOut()
            ) {
                // Use the darker color for the dialog background
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("OK", color = PrimaryYellow)
                        }
                    },
                    title = {
                        Text(
                            if (isSuccess) "Verification Successful" else "Verification Failed",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    text = {
                        // Circle animation for success/failure
                        val transition = rememberInfiniteTransition(label = "")
                        val scale by transition.animateFloat(
                            initialValue = 1f,
                            targetValue = 1.1f,
                            animationSpec = infiniteRepeatable(
                                tween(700, easing = FastOutSlowInEasing),
                                RepeatMode.Reverse
                            ), label = ""
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .scale(scale)
                                    .background(
                                        if (isSuccess) Color(0xFF4CAF50) // Green
                                        else Color(0xFFE53935), // Red
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isSuccess) "✓" else "✕",
                                    color = Color.White,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    },
                    containerColor = DarkBlue, // Dark dialog background
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }
    }
}
