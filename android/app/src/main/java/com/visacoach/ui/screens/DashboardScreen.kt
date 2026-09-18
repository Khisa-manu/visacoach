package com.visacoach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.ui.components.VisaCard
import com.visacoach.ui.theme.*
import com.visacoach.ui.viewmodels.PaymentViewModel

@Composable
fun DashboardScreen(
    paymentViewModel: PaymentViewModel,
    onStartInterview: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSubscription: () -> Unit,
    onNavigateToPractice: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val subscription by paymentViewModel.subscription.collectAsState()

    Scaffold(
        containerColor = Neutral50,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToPractice,
                    icon = { Icon(Icons.Default.Chat, contentDescription = null) },
                    label = { Text("Practice") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToHistory,
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    label = { Text("History") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToProfile,
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = PrimaryNavy,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                "USA VisaCoach",
                                style = VisaCoachTypography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryNavy
                            )
                            Text(
                                "B1/B2 Prep",
                                style = VisaCoachTypography.labelSmall,
                                color = Neutral500
                            )
                        }
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Neutral600)
                    }
                }
            }

            // Hero Card - Start Interview
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = ShapeCardLg,
                    colors = CardDefaults.cardColors(containerColor = PrimaryNavy)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(PrimaryNavy, PrimaryNavyLight)
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Mic,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    "Start Live AI Interview",
                                    style = VisaCoachTypography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Real-time voice simulated consular interview",
                                style = VisaCoachTypography.bodySmall,
                                color = Neutral300
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = onStartInterview,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF38BDF8),
                                    contentColor = PrimaryNavy
                                ),
                                shape = ShapeButton,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Start Live AI Interview", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Progress Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Your Progress",
                        style = VisaCoachTypography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                    Text(
                        "Updated today",
                        style = VisaCoachTypography.labelSmall,
                        color = Neutral500
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProgressCard(
                        title = "Financial Ties",
                        score = 7.2f,
                        max = 10f,
                        strength = "Developing",
                        color = Info,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToPractice
                    )
                    ProgressCard(
                        title = "Travel Intent",
                        score = 8.1f,
                        max = 10f,
                        strength = "Strong",
                        color = Success,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToPractice
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ProgressCard(
    title: String,
    score: Float,
    max: Float,
    strength: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = ShapeCard,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                style = VisaCoachTypography.labelMedium,
                color = Neutral600
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                String.format("%.1f", score),
                style = VisaCoachTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryNavy
            )
            Text(
                "/ ${max.toInt()}",
                style = VisaCoachTypography.labelSmall,
                color = Neutral400
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Strength: $strength",
                style = VisaCoachTypography.labelSmall,
                color = color,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Simple progress ring placeholder
            CircularProgressIndicator(
                progress = { score / max },
                modifier = Modifier.size(56.dp),
                color = color,
                trackColor = Neutral200,
                strokeWidth = 6.dp
            )
        }
    }
}