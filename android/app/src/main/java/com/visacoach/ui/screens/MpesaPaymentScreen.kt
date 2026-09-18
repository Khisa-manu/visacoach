package com.visacoach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.visacoach.ui.theme.PrimaryNavy
import com.visacoach.ui.theme.SafaricomGreen
import com.visacoach.ui.viewmodels.PaymentUiState
import com.visacoach.ui.viewmodels.PaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MpesaPaymentScreen(
    viewModel: PaymentViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var phone by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("M-Pesa Express Upgrade", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Plan Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryNavy)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("VisaCoach Pro", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Box(
                                modifier = Modifier
                                    .background(SafaricomGreen, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("Lipa na M-Pesa", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "KES 1,499",
                            color = Color(0xFF38BDF8),
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        )
                        Text(
                            "30 Days Unlimited Real-Time AI Mock Interviews & Comprehensive Evaluation Reports",
                            color = Color(0xFFCBD5E1),
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                when (val state = uiState) {
                    is PaymentUiState.Idle, is PaymentUiState.Initiating -> {
                        Text(
                            "Enter the Safaricom M-Pesa number to receive the prompt:",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("M-Pesa Number") },
                            placeholder = { Text("0712 345 678") },
                            leadingIcon = { Icon(Icons.Default.Phone, null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            enabled = state !is PaymentUiState.Initiating
                        )
                    }

                    is PaymentUiState.AwaitingPin -> {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(color = SafaricomGreen, modifier = Modifier.size(40.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Prompt Sent to Your Phone", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SafaricomGreen)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Please check your Safaricom mobile screen and enter your M-Pesa Secret PIN to confirm KES 1,499.",
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF166534)
                                )
                            }
                        }
                    }

                    is PaymentUiState.Completed -> {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SafaricomGreen, modifier = Modifier.size(48.dp))
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Payment Successful!", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = SafaricomGreen)
                                Text("M-Pesa Receipt: ${state.receipt}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = onSuccess,
                                    colors = ButtonDefaults.buttonColors(containerColor = SafaricomGreen)
                                ) {
                                    Text("Return to Dashboard")
                                }
                            }
                        }
                    }

                    is PaymentUiState.Failed -> {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.Error, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(44.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Payment Incomplete", fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(state.reason, fontSize = 12.sp, textAlign = TextAlign.Center, color = Color(0xFF7F1D1D))
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(onClick = { viewModel.reset() }) {
                                    Text("Try Again")
                                }
                            }
                        }
                    }
                }
            }

            // Bottom CTA
            if (uiState is PaymentUiState.Idle || uiState is PaymentUiState.Initiating) {
                Button(
                    onClick = { viewModel.initiateMpesaStkPush(phone, "PREMIUM") },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SafaricomGreen),
                    shape = RoundedCornerShape(12.dp),
                    enabled = phone.isNotBlank() && uiState !is PaymentUiState.Initiating
                ) {
                    if (uiState is PaymentUiState.Initiating) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
                    } else {
                        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pay KES 1,499 via M-Pesa", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
