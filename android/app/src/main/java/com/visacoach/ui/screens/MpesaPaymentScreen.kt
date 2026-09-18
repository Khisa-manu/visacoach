package com.visacoach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.visacoach.ui.components.SafaricomButton
import com.visacoach.ui.components.VisaMpesaPhoneField
import com.visacoach.ui.theme.*
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
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Upgrade to Pro", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Neutral50)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Plan Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = ShapeCardLg,
                    colors = CardDefaults.cardColors(containerColor = PrimaryNavy)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    "VisaCoach Pro",
                                    color = Color.White,
                                    style = VisaCoachTypography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "KES 1,499",
                                    color = Color(0xFF38BDF8),
                                    style = VisaCoachTypography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Surface(
                                color = SafaricomGreen,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    "Lipa na M-Pesa",
                                    color = Color.White,
                                    style = VisaCoachTypography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Pro includes:",
                    style = VisaCoachTypography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(12.dp))

                listOf(
                    "Unlimited mock interviews",
                    "Deep evaluation report",
                    "Weak area practice",
                    "Priority support"
                ).forEach { benefit ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Success,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(benefit, style = VisaCoachTypography.bodyMedium, color = Neutral700)
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                VisaMpesaPhoneField(
                    phone = phone,
                    onPhoneChange = { phone = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                SafaricomButton(
                    text = "Pay with M-Pesa",
                    onClick = {
                        viewModel.initiateStkPush(phone)
                    },
                    loading = uiState is com.visacoach.ui.viewmodels.PaymentUiState.Loading
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "You will receive an M-Pesa prompt on your phone (STK Push)",
                    style = VisaCoachTypography.labelSmall,
                    color = Neutral500,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}