package com.visacoach.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.ui.theme.PrimaryNavy
import com.visacoach.ui.theme.SafaricomGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisaTypeScreen(onBack: () -> Unit, onStartPrep: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Visa Category: B1/B2", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = PrimaryNavy)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("B1/B2 Temporary Visitor Visa", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "For business (B-1) or tourism and medical treatment (B-2). Interviewed at the U.S. Embassy in Nairobi (Gigiri) under INA section 214(b).",
                            color = Color(0xFFCBD5E1),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
            item {
                Text("Core Legal Presumption (INA 214(b))", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    "Under U.S. immigration law, every applicant is presumed to be an intending immigrant until they establish that they qualify for nonimmigrant status by showing strong social and economic ties to Kenya.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
            item {
                Button(
                    onClick = onStartPrep,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Proceed to Interview Prep Guide")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewPrepScreen(onBack: () -> Unit, onStartMock: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Interview Preparation Guide", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("Essential Documents to Bring", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            val docs = listOf(
                "Valid Passport (valid for at least 6 months beyond intended stay)",
                "DS-160 Confirmation Page with barcode",
                "Appointment Confirmation Letter",
                "One 2x2 inch photograph (if requested)",
                "Proof of employment and approved leave from your employer",
                "Bank statements showing adequate travel funds"
            )
            items(docs) { doc ->
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF059669), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(doc, fontSize = 13.sp, lineHeight = 18.sp)
                }
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onStartMock,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Start Mock Voice Interview Now")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeWeakAreasScreen(onBack: () -> Unit, onSelectCategory: (String) -> Unit) {
    val categories = listOf(
        Pair("TRAVEL_PURPOSE", "Travel Purpose & Schedule"),
        Pair("EMPLOYMENT", "Employment, Business & Career Ties"),
        Pair("FINANCIAL_ABILITY", "Trip Funding & Proof of Funds"),
        Pair("ACCOMMODATION", "Accommodation & Destination Plans"),
        Pair("TIES_TO_HOME_COUNTRY", "Economic & Family Ties to Kenya"),
        Pair("TRAVEL_HISTORY", "Previous International Travel"),
        Pair("US_CONNECTIONS", "Contacts and Family in the U.S."),
        Pair("RETURN_INTENT", "Intent to Return to Kenya")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Practice Specific Areas", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text("Select a topic to drill targeted questions:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(4.dp))
            }
            items(categories) { (code, title) ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onSelectCategory(code) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Icon(Icons.Default.ChevronRight, null)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Interview History", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(3) { idx ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("B1/B2 Visitor Visa Simulation", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Score: 7.${8 - idx}/10", fontWeight = FontWeight.Bold, color = PrimaryNavy, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Session #${3 - idx} • 7 Questions • Completed", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(onBack: () -> Unit, onUpgradeMpesa: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subscription Plans", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryNavy)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("VisaCoach Pro", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("KES 1,499 for 30 Days", color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("• Unlimited real-time voice AI mock interviews", color = Color(0xFFE2E8F0), fontSize = 13.sp)
                        Text("• In-depth evaluation & consistency analysis", color = Color(0xFFE2E8F0), fontSize = 13.sp)
                        Text("• Weak-area practice drills", color = Color(0xFFE2E8F0), fontSize = 13.sp)
                        Text("• Instant activation via Safaricom M-Pesa", color = Color(0xFFE2E8F0), fontSize = 13.sp)
                    }
                }
            }

            Button(
                onClick = onUpgradeMpesa,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SafaricomGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Upgrade with M-Pesa (KES 1,499)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onNavigateToProfile() },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, null)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Applicant Profile", fontWeight = FontWeight.Medium)
                        }
                        Icon(Icons.Default.ChevronRight, null)
                    }
                }
            }
            item {
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Legal Disclaimer", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "USA VisaCoach is an educational practice tool. It is not affiliated with, endorsed by, or representing the U.S. Department of State, the U.S. Embassy in Nairobi, or any consular officer. It does not predict or guarantee visa outcomes.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626))
                ) {
                    Icon(Icons.Default.Logout, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out")
                }
            }
        }
    }
}
