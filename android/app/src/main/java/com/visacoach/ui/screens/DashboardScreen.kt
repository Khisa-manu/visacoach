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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.ui.theme.PrimaryNavy
import com.visacoach.ui.theme.SafaricomGreen
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
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("USA VisaCoach", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = PrimaryNavy)
                    Text("B1/B2 Visitor Visa Preparation", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = onNavigateToSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToPractice,
                    icon = { Icon(Icons.Default.Psychology, null) },
                    label = { Text("Practice") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToProfile,
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToSubscription,
                    icon = { Icon(Icons.Default.Star, null) },
                    label = { Text("Plans") }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Subscription Status Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (subscription?.isPremium == true) Color(0xFF064E3B) else Color(0xFFF1F5F9)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = if (subscription?.isPremium == true) "PREMIUM ACCESS ACTIVE" else "FREE STARTER TIER",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (subscription?.isPremium == true) Color(0xFF34D399) else Color(0xFF475569)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (subscription?.isPremium == true) "Unlimited AI practice & in-depth analysis" else "1 Free Mock Interview remaining",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (subscription?.isPremium == true) Color.White else PrimaryNavy
                            )
                        }
                        if (subscription?.isPremium != true) {
                            Button(
                                onClick = onNavigateToSubscription,
                                colors = ButtonDefaults.buttonColors(containerColor = SafaricomGreen),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("M-Pesa Upgrade", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Big Start Interview CTA Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onStartInterview() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryNavy)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(Color.White.copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Mic, contentDescription = null, tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text("Start Voice Mock Interview", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("Real-time AI Simulated Consular Interview", color = Color(0xFF94A3B8), fontSize = 12.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            "The simulated interviewer speaks aloud in English and listens naturally to your responses via your microphone.",
                            color = Color(0xFFE2E8F0),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onStartInterview,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Launch Live Session", color = PrimaryNavy, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Quick Actions & Practice Progress
            item {
                Text("Practice Performance & Weak Areas", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToPractice() },
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(Icons.Default.Assessment, contentDescription = null, tint = Color(0xFF2563EB))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Financial Ties", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Score: 7.2 / 10", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToPractice() },
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = Color(0xFF0D9488))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Trip Purpose", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Score: 8.5 / 10", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Recent Interview History
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Past Mock Interviews", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(
                        "View All",
                        fontSize = 13.sp,
                        color = Color(0xFF2563EB),
                        modifier = Modifier.clickable { onNavigateToHistory() }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("B1/B2 Mock Session #1", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                            Text("7 Questions • 6m 20s • Relevance 8.0/10", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null)
                    }
                }
            }
        }
    }
}
