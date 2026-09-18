package com.visacoach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.ui.components.BadgeType
import com.visacoach.ui.components.PrimaryButton
import com.visacoach.ui.components.SafaricomButton
import com.visacoach.ui.components.SecondaryButton
import com.visacoach.ui.components.VisaCard
import com.visacoach.ui.components.VisaCardLg
import com.visacoach.ui.components.VisaCategoryChip
import com.visacoach.ui.components.VisaMicRecordButton
import com.visacoach.ui.components.VisaMpesaPhoneField
import com.visacoach.ui.components.VisaStatusBadge
import com.visacoach.ui.components.VisaTextField
import com.visacoach.ui.components.VisaTextButton
import com.visacoach.ui.theme.VisaCoachTheme

/**
 * USA VisaCoach - Design System / UI Kit Showcase Screen
 * Demonstrates the complete design tokens, components, and user flows.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiKitShowcaseScreen(
    onBack: () -> Unit = {}
) {
    var samplePhone by remember { mutableStateOf("0712345678") }
    var sampleApplicantName by remember { mutableStateOf("Brian Mwangi") }
    var isRecording by remember { mutableStateOf(false) }
    var isMpesaLoading by remember { mutableStateOf(false) }
    var isPrimaryLoading by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("B1/B2 Tourism") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "USA VisaCoach UI Kit",
                            style = VisaCoachTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Design System & Production Components",
                            style = VisaCoachTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VisaCoachTheme.colors.primaryNavy
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(VisaCoachTheme.materialColors.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(VisaCoachTheme.dimens.space20),
            verticalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space24)
        ) {
            // -----------------------------------------------------------------
            // SECTION 1: Category Chips & Badges
            // -----------------------------------------------------------------
            Column(verticalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space8)) {
                Text(
                    text = "STATUS BADGES & VISA CATEGORIES",
                    style = VisaCoachTheme.typography.labelSmall,
                    color = VisaCoachTheme.colors.textSecondary,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space8),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VisaStatusBadge(text = "92% Strong Ties", type = BadgeType.SUCCESS)
                    VisaStatusBadge(text = "Section 214(b) Risk", type = BadgeType.WARNING)
                    VisaStatusBadge(text = "DS-160 Verified", type = BadgeType.NAVY)
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space8),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("B1/B2 Tourism", "Conference Attendee", "Family Visit").forEach { cat ->
                        VisaCategoryChip(
                            text = cat,
                            selected = selectedTab == cat,
                            onClick = { selectedTab = cat }
                        )
                    }
                }
            }

            // -----------------------------------------------------------------
            // SECTION 2: Standard Card (Consular Question & Audio Wave)
            // -----------------------------------------------------------------
            Column(verticalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space8)) {
                Text(
                    text = "STANDARD CARD (VISA CARD)",
                    style = VisaCoachTheme.typography.labelSmall,
                    color = VisaCoachTheme.colors.textSecondary,
                    fontWeight = FontWeight.Bold
                )

                VisaCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VisaStatusBadge(text = "Question 3 of 10", type = BadgeType.INFO)
                        Text(
                            text = "Nairobi Embassy",
                            style = VisaCoachTheme.typography.bodySmall,
                            color = VisaCoachTheme.colors.textSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(VisaCoachTheme.dimens.space12))

                    Text(
                        text = "“What is the exact purpose of your trip to the United States, and who will sponsor your stay?”",
                        style = VisaCoachTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = VisaCoachTheme.colors.textPrimary
                    )

                    Spacer(modifier = Modifier.height(VisaCoachTheme.dimens.space12))

                    Text(
                        text = "Tip: State your conference itinerary or vacation plan clearly. Mention your employer in Nairobi without hesitating.",
                        style = VisaCoachTheme.typography.bodySmall,
                        color = VisaCoachTheme.colors.textSecondary
                    )
                }
            }

            // -----------------------------------------------------------------
            // SECTION 3: Large Featured Card (M-Pesa Buy Goods Plan)
            // -----------------------------------------------------------------
            Column(verticalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space8)) {
                Text(
                    text = "FEATURED CARD (VISA CARD LG)",
                    style = VisaCoachTheme.typography.labelSmall,
                    color = VisaCoachTheme.colors.textSecondary,
                    fontWeight = FontWeight.Bold
                )

                VisaCardLg(
                    accentColor = VisaCoachTheme.colors.safaricomGreen,
                    headerBadge = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            VisaStatusBadge(text = "LIPA NA M-PESA BUY GOODS", type = BadgeType.SAFARICOM)
                            Text(
                                text = "Till No: 982143",
                                style = VisaCoachTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = VisaCoachTheme.colors.textSecondary
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Unlimited Embassy Voice Mocks",
                        style = VisaCoachTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = VisaCoachTheme.colors.textPrimary
                    )

                    Spacer(modifier = Modifier.height(VisaCoachTheme.dimens.space4))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "KES 499",
                            style = VisaCoachTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = VisaCoachTheme.colors.primaryNavy
                        )
                        Spacer(modifier = Modifier.width(VisaCoachTheme.dimens.space8))
                        Text(
                            text = "/ 30 days full access",
                            style = VisaCoachTheme.typography.bodyMedium,
                            color = VisaCoachTheme.colors.textSecondary,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(VisaCoachTheme.dimens.space12))

                    listOf(
                        "AI Consular Officer real-time speech assessment",
                        "214(b) immigrant intent risk score & advice",
                        "DS-160 consistency check & response transcripts"
                    ).forEach { feature ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = VisaCoachTheme.colors.safaricomGreen,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = feature,
                                style = VisaCoachTheme.typography.bodyMedium,
                                color = VisaCoachTheme.colors.textPrimary
                            )
                        }
                    }
                }
            }

            // -----------------------------------------------------------------
            // SECTION 4: Reusable Input Fields
            // -----------------------------------------------------------------
            Column(verticalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space12)) {
                Text(
                    text = "STANDARDIZED TEXT FIELDS",
                    style = VisaCoachTheme.typography.labelSmall,
                    color = VisaCoachTheme.colors.textSecondary,
                    fontWeight = FontWeight.Bold
                )

                VisaTextField(
                    value = sampleApplicantName,
                    onValueChange = { sampleApplicantName = it },
                    label = "Passport Full Name (As on DS-160)",
                    placeholder = "e.g. Brian Mwangi",
                    supportingText = "Ensure exact match with your Kenyan passport bio-page"
                )

                VisaMpesaPhoneField(
                    phone = samplePhone,
                    onPhoneChange = { samplePhone = it }
                )
            }

            // -----------------------------------------------------------------
            // SECTION 5: Production Action Buttons
            // -----------------------------------------------------------------
            Column(verticalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space12)) {
                Text(
                    text = "ACTION BUTTONS & LOADING STATES",
                    style = VisaCoachTheme.typography.labelSmall,
                    color = VisaCoachTheme.colors.textSecondary,
                    fontWeight = FontWeight.Bold
                )

                // Safaricom Button
                SafaricomButton(
                    text = "Pay KES 499 with M-Pesa",
                    subtext = "Instant STK push notification on your phone",
                    loading = isMpesaLoading,
                    onClick = {
                        isMpesaLoading = true
                    }
                )

                // Primary Navy Button
                PrimaryButton(
                    text = "Start AI Mock Interview",
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    loading = isPrimaryLoading,
                    onClick = {
                        isPrimaryLoading = true
                    }
                )

                // Secondary Outlined Button
                SecondaryButton(
                    text = "Practice High-Risk 214(b) Questions",
                    onClick = {
                        isMpesaLoading = false
                        isPrimaryLoading = false
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    VisaTextButton(
                        text = "Reset Button Loading States",
                        onClick = {
                            isMpesaLoading = false
                            isPrimaryLoading = false
                        }
                    )
                }
            }

            // -----------------------------------------------------------------
            // SECTION 6: Voice Interview Microphone Control
            // -----------------------------------------------------------------
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(VisaCoachTheme.dimens.space8)
            ) {
                Text(
                    text = "AI VOICE INTERACTION CONTROL",
                    style = VisaCoachTheme.typography.labelSmall,
                    color = VisaCoachTheme.colors.textSecondary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (isRecording) "Recording your answer... (Tap to finish)" else "Tap to answer in natural English",
                    style = VisaCoachTheme.typography.bodyMedium,
                    color = if (isRecording) VisaCoachTheme.colors.micRecording else VisaCoachTheme.colors.textSecondary
                )

                VisaMicRecordButton(
                    isRecording = isRecording,
                    onClick = { isRecording = !isRecording }
                )
            }
        }
    }
}
