package com.visacoach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.ui.components.PrimaryButton
import com.visacoach.ui.components.SecondaryButton
import com.visacoach.ui.components.VisaTextField
import com.visacoach.ui.components.VisaMpesaPhoneField
import com.visacoach.ui.theme.*
import com.visacoach.ui.viewmodels.AuthUiState
import com.visacoach.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToWelcome: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500)
        onNavigateToWelcome()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PrimaryNavy
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.screenHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = null,
                    tint = AccentTeal,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "USA VisaCoach",
                style = VisaCoachTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "AI Voice Mock Interview Prep",
                style = VisaCoachTypography.bodyMedium,
                color = Neutral300
            )

            Spacer(modifier = Modifier.height(48.dp))

            CircularProgressIndicator(
                color = AccentTeal,
                modifier = Modifier.size(28.dp),
                strokeWidth = 2.5.dp
            )
        }
    }
}

@Composable
fun WelcomeScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Neutral50
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.screenHorizontal)
                .padding(top = 48.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo + Brand
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = AccentTeal,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "USA VisaCoach",
                        style = VisaCoachTypography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Main Icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White, RoundedCornerShape(28.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Master Your\nU.S. Visa Interview",
                    style = VisaCoachTypography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Realistic AI voice mock interviews for B1/B2 applicants in Kenya. Build confidence before your appointment.",
                    style = VisaCoachTypography.bodyMedium,
                    color = Neutral600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }

            // Buttons
            Column(modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(
                    text = "Get Started",
                    onClick = onNavigateToRegister
                )
                Spacer(modifier = Modifier.height(12.dp))
                SecondaryButton(
                    text = "I already have an account",
                    onClick = onNavigateToLogin
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToOtp: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.OtpSent) {
            onNavigateToOtp((uiState as AuthUiState.OtpSent).phone)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Neutral50
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.screenHorizontal)
                .padding(top = 40.dp, bottom = 32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = AccentTeal,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "USA VisaCoach",
                        style = VisaCoachTypography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Create Account",
                    style = VisaCoachTypography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sign up with your Kenyan mobile number to start realistic visa mock interviews.",
                    style = VisaCoachTypography.bodyMedium,
                    color = Neutral600
                )

                Spacer(modifier = Modifier.height(28.dp))

                VisaTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Full Legal Name",
                    placeholder = "As shown on your passport / DS-160",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                VisaMpesaPhoneField(
                    phone = phone,
                    onPhoneChange = { phone = it },
                    modifier = Modifier.fillMaxWidth()
                )

                if (uiState is AuthUiState.Error) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = (uiState as AuthUiState.Error).message,
                        color = Error,
                        style = VisaCoachTypography.bodySmall
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
                PrimaryButton(
                    text = "Send Verification OTP",
                    onClick = { viewModel.register(phone, fullName) },
                    loading = uiState is AuthUiState.Loading,
                    enabled = fullName.isNotBlank() && phone.isNotBlank()
                )
                Spacer(modifier = Modifier.height(12.dp))
                SecondaryButton(
                    text = "Already have an account? Sign In",
                    onClick = onNavigateToLogin
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToOtp: (String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.OtpSent) {
            onNavigateToOtp((uiState as AuthUiState.OtpSent).phone)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Neutral50
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.screenHorizontal)
                .padding(top = 40.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = AccentTeal,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "USA VisaCoach",
                        style = VisaCoachTypography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Welcome Back",
                    style = VisaCoachTypography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sign in with your registered Kenyan phone number.",
                    style = VisaCoachTypography.bodyMedium,
                    color = Neutral600
                )

                Spacer(modifier = Modifier.height(28.dp))

                VisaMpesaPhoneField(
                    phone = phone,
                    onPhoneChange = { phone = it },
                    modifier = Modifier.fillMaxWidth()
                )

                if (uiState is AuthUiState.Error) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = (uiState as AuthUiState.Error).message,
                        color = Error,
                        style = VisaCoachTypography.bodySmall
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(
                    text = "Request OTP Code",
                    onClick = { viewModel.login(phone) },
                    loading = uiState is AuthUiState.Loading,
                    enabled = phone.isNotBlank()
                )
                Spacer(modifier = Modifier.height(12.dp))
                SecondaryButton(
                    text = "Don't have an account? Register",
                    onClick = onNavigateToRegister
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    phoneNumber: String,
    viewModel: AuthViewModel,
    onVerified: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var otpCode by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Authenticated) {
            onVerified()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Neutral50
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.screenHorizontal)
                .padding(top = 40.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryNavy
                        )
                    }
                    Text(
                        text = "Verification",
                        style = VisaCoachTypography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Enter OTP Code",
                    style = VisaCoachTypography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "A 6-digit verification code was sent to $phoneNumber.",
                    style = VisaCoachTypography.bodyMedium,
                    color = Neutral600
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "6-Digit Code",
                    style = VisaCoachTypography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = otpCode,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }
                        if (digits.length <= 6) otpCode = digits
                    },
                    placeholder = { Text("123456") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = ShapeTextField,
                    modifier = Modifier.fillMaxWidth()
                )

                if (uiState is AuthUiState.Error) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = (uiState as AuthUiState.Error).message,
                        color = Error,
                        style = VisaCoachTypography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = { viewModel.login(phoneNumber) }
                ) {
                    Text(
                        text = "Didn't receive code? Resend OTP",
                        color = AccentTeal,
                        style = VisaCoachTypography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(
                    text = "Verify & Continue",
                    onClick = { viewModel.verifyOtp(phoneNumber, otpCode) },
                    loading = uiState is AuthUiState.Loading,
                    enabled = otpCode.length >= 4
                )
                Spacer(modifier = Modifier.height(12.dp))
                SecondaryButton(
                    text = "Back",
                    onClick = onBack
                )
            }
        }
    }
}