package com.visacoach.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.visacoach.ui.components.PrimaryButton
import com.visacoach.ui.components.SafaricomButton
import com.visacoach.ui.components.VisaCard
import com.visacoach.ui.components.VisaCardLg
import com.visacoach.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisaTypeScreen(
    onBack: () -> Unit,
    onStartPrep: () -> Unit
) {
    Scaffold(
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Visa Category: B1/B2", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Neutral50)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal, vertical = Dimens.screenVertical),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                VisaCardLg(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "B1/B2 Temporary Visitor Visa",
                        color = Color.White,
                        style = VisaCoachTypography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "For business (B-1) or tourism, medical treatment, and visiting family (B-2). Consular interviews take place at the U.S. Embassy in Nairobi (Gigiri) under INA Section 214(b).",
                        color = Neutral200,
                        style = VisaCoachTypography.bodyMedium,
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                VisaCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Core Legal Presumption (INA 214(b))",
                        fontWeight = FontWeight.Bold,
                        style = VisaCoachTypography.titleSmall,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Under U.S. immigration law, every applicant is legally presumed to be an intending immigrant until they establish strong social and economic ties binding them to Kenya (stable job, family, property, business).",
                        style = VisaCoachTypography.bodyMedium,
                        color = Neutral700,
                        lineHeight = 20.sp
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryButton(
                    text = "Proceed to Interview Prep Guide",
                    onClick = onStartPrep
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewPrepScreen(
    onBack: () -> Unit,
    onStartMock: () -> Unit
) {
    Scaffold(
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Interview Preparation Guide", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Neutral50)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal, vertical = Dimens.screenVertical),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    "Essential Documents for Nairobi Embassy",
                    style = VisaCoachTypography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Ensure you have these readily organized before your consular appointment:",
                    style = VisaCoachTypography.bodySmall,
                    color = Neutral600
                )
            }

            val docs = listOf(
                "Valid Kenyan Passport (valid for at least 6 months beyond intended stay)",
                "DS-160 Confirmation Page with clear scannable barcode",
                "Embassy Appointment Confirmation & MRV Fee Receipt",
                "One 2x2 inch recent passport photograph meeting U.S. requirements",
                "Official letter of employment & approved leave from employer",
                "Recent 3-6 months official bank statements showing adequate travel funds"
            )

            items(docs) { doc ->
                VisaCard(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Success,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            doc,
                            style = VisaCoachTypography.bodyMedium,
                            color = Neutral800,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                PrimaryButton(
                    text = "Start AI Voice Mock Interview",
                    onClick = onStartMock
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeWeakAreasScreen(
    onBack: () -> Unit,
    onSelectCategory: (String) -> Unit = {}
) {
    val categories = listOf(
        Pair("TRAVEL_PURPOSE", "Travel Purpose & Daily Itinerary"),
        Pair("EMPLOYMENT", "Employment, Business & Career Ties"),
        Pair("FINANCIAL_ABILITY", "Trip Funding & Proof of Funds"),
        Pair("ACCOMMODATION", "Accommodation & Destination Plans"),
        Pair("TIES_TO_HOME_COUNTRY", "Economic & Family Ties to Kenya"),
        Pair("TRAVEL_HISTORY", "Previous International Travel History"),
        Pair("US_CONNECTIONS", "Contacts, Relatives & Friends in the U.S."),
        Pair("RETURN_INTENT", "Intent & Obligation to Return to Kenya")
    )

    Scaffold(
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Practice Specific Areas", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Neutral50)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal, vertical = Dimens.screenVertical),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    "Select a weak area to drill targeted questions:",
                    style = VisaCoachTypography.bodyMedium,
                    color = Neutral600
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(categories) { (code, title) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectCategory(code) },
                    shape = ShapeCard,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            title,
                            fontWeight = FontWeight.SemiBold,
                            style = VisaCoachTypography.bodyMedium,
                            color = PrimaryNavy
                        )
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Neutral400
                        )
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
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Interview History", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Neutral50)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal, vertical = Dimens.screenVertical),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(3) { idx ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = ShapeCard,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "B1/B2 Mock Interview",
                                fontWeight = FontWeight.Bold,
                                style = VisaCoachTypography.titleSmall,
                                color = PrimaryNavy
                            )
                            Surface(
                                shape = ShapeChip,
                                color = AccentTeal.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    "Score: 7.${8 - idx}/10",
                                    fontWeight = FontWeight.Bold,
                                    color = AccentTeal,
                                    style = VisaCoachTypography.labelMedium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Session #${3 - idx} • 7 Questions Completed • Nairobi B1/B2 Profile",
                            style = VisaCoachTypography.bodySmall,
                            color = Neutral500
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(
    onBack: () -> Unit,
    onUpgradeMpesa: () -> Unit
) {
    Scaffold(
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Subscription Plans", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
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
                .padding(horizontal = Dimens.screenHorizontal, vertical = Dimens.screenVertical),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                VisaCardLg(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "VisaCoach Pro",
                                color = Color.White,
                                style = VisaCoachTypography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "KES 1,499 / Month",
                                color = Color(0xFF38BDF8),
                                style = VisaCoachTypography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Surface(
                            color = SafaricomGreen,
                            shape = ShapeChip
                        ) {
                            Text(
                                "M-Pesa",
                                color = Color.White,
                                style = VisaCoachTypography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    listOf(
                        "Unlimited real-time voice AI mock interviews",
                        "In-depth consular evaluation & scoring",
                        "Targeted weak-area practice drills",
                        "Instant activation via Safaricom Lipa na M-Pesa"
                    ).forEach { perk ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AccentTeal,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                perk,
                                color = Neutral100,
                                style = VisaCoachTypography.bodyMedium
                            )
                        }
                    }
                }
            }

            SafaricomButton(
                text = "Upgrade with M-Pesa (KES 1,499)",
                onClick = onUpgradeMpesa
            )
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
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Neutral50)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal, vertical = Dimens.screenVertical),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToProfile() },
                    shape = ShapeCard,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryNavy)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Applicant Profile (DS-160)",
                                fontWeight = FontWeight.Medium,
                                style = VisaCoachTypography.bodyMedium,
                                color = PrimaryNavy
                            )
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Neutral400)
                    }
                }
            }

            item {
                VisaCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Legal & Practice Disclaimer",
                        fontWeight = FontWeight.Bold,
                        style = VisaCoachTypography.labelMedium,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "USA VisaCoach is an educational practice tool. It is not affiliated with, endorsed by, or representing the U.S. Department of State, the U.S. Embassy in Nairobi, or any consular officer. It does not predict or guarantee visa issuance.",
                        style = VisaCoachTypography.bodySmall,
                        color = Neutral600,
                        lineHeight = 16.sp
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.buttonHeight),
                    shape = ShapeButton,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Error),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Error.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.Default.Logout, contentDescription = null, tint = Error)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Sign Out",
                        style = VisaCoachTypography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Error
                    )
                }
            }
        }
    }
}
