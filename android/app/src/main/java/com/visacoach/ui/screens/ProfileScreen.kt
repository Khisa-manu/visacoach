package com.visacoach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.domain.models.ProfileModel
import com.visacoach.ui.components.PrimaryButton
import com.visacoach.ui.components.VisaTextField
import com.visacoach.ui.theme.*
import com.visacoach.ui.viewmodels.ProfileUiState
import com.visacoach.ui.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onNavigateToSubscription: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Applicant Profile", fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Neutral50)
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ProfileUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.loadProfile() }) { Text("Retry") }
                    }
                }
            }
            is ProfileUiState.Success -> {
                ProfileForm(
                    profile = state.profile,
                    isSaving = state.isSaving,
                    saveMessage = state.saveMessage,
                    onSave = { updated -> viewModel.updateProfile(updated) },
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun ProfileForm(
    profile: ProfileModel,
    isSaving: Boolean,
    saveMessage: String?,
    onSave: (ProfileModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf(profile.fullName) }
    var purposeOfTravel by remember { mutableStateOf(profile.purposeOfTravel) }
    var intendedTravelDate by remember { mutableStateOf(profile.intendedTravelDate ?: "") }
    var intendedDuration by remember { mutableStateOf(profile.intendedDuration ?: "") }
    var occupation by remember { mutableStateOf(profile.occupation ?: "") }
    var employer by remember { mutableStateOf(profile.employer ?: "") }
    var employmentDuration by remember { mutableStateOf(profile.employmentDuration ?: "") }
    var incomeRange by remember { mutableStateOf(profile.incomeRange ?: "") }
    var sponsorType by remember { mutableStateOf(profile.sponsorType ?: "") }
    var internationalTravelHistory by remember { mutableStateOf(profile.internationalTravelHistory ?: "") }
    var usVisaHistory by remember { mutableStateOf(profile.usVisaHistory ?: "") }
    var usFamilyInfo by remember { mutableStateOf(profile.usFamilyInfo ?: "") }
    var accommodationDetails by remember { mutableStateOf(profile.accommodationDetails ?: "") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF))
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "Information entered here is used exclusively to simulate realistic interview questions matching your application. Always state your truthful circumstances as entered on your DS-160 form.",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Color(0xFF1E3A8A)
                    )
                }
            }
        }

        item {
            VisaTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name (as in Passport)",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            VisaTextField(
                value = purposeOfTravel,
                onValueChange = { purposeOfTravel = it },
                label = "Primary Purpose of Travel",
                placeholder = "e.g. Tourism, visiting family, attending conference",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VisaTextField(
                    value = intendedTravelDate,
                    onValueChange = { intendedTravelDate = it },
                    label = "Intended Date",
                    placeholder = "e.g. Nov 2026",
                    modifier = Modifier.weight(1f)
                )
                VisaTextField(
                    value = intendedDuration,
                    onValueChange = { intendedDuration = it },
                    label = "Duration",
                    placeholder = "e.g. 2 weeks",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            VisaTextField(
                value = occupation,
                onValueChange = { occupation = it },
                label = "Occupation / Professional Title",
                placeholder = "e.g. Software Engineer, Accountant, Business Owner",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VisaTextField(
                    value = employer,
                    onValueChange = { employer = it },
                    label = "Employer / Company",
                    modifier = Modifier.weight(1.2f)
                )
                VisaTextField(
                    value = employmentDuration,
                    onValueChange = { employmentDuration = it },
                    label = "Tenure",
                    placeholder = "e.g. 3 years",
                    modifier = Modifier.weight(0.8f)
                )
            }
        }

        item {
            VisaTextField(
                value = incomeRange,
                onValueChange = { incomeRange = it },
                label = "Monthly Income Range (KES)",
                placeholder = "e.g. KES 150,000 - 250,000",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            VisaTextField(
                value = sponsorType,
                onValueChange = { sponsorType = it },
                label = "Trip Funding / Who is Paying",
                placeholder = "e.g. Self-funded from savings, Employer sponsored",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            VisaTextField(
                value = internationalTravelHistory,
                onValueChange = { internationalTravelHistory = it },
                label = "Prior International Travel History",
                placeholder = "e.g. Rwanda (2023), South Africa (2024), UAE (2025)",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            VisaTextField(
                value = usVisaHistory,
                onValueChange = { usVisaHistory = it },
                label = "U.S. Visa History",
                placeholder = "e.g. First time applicant, or 214(b) refusal in 2022",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            VisaTextField(
                value = usFamilyInfo,
                onValueChange = { usFamilyInfo = it },
                label = "Immediate Family / Relatives in the U.S.",
                placeholder = "e.g. None, or sister lives in Dallas on Green Card",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            VisaTextField(
                value = accommodationDetails,
                onValueChange = { accommodationDetails = it },
                label = "Accommodation Details",
                placeholder = "e.g. Marriott Hotel Manhattan, or staying with friend",
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (!saveMessage.isNullOrBlank()) {
            item {
                Text(saveMessage, color = Color(0xFF059669), fontSize = 13.sp)
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            PrimaryButton(
                text = "Save & Update Profile",
                onClick = {
                    onSave(
                        profile.copy(
                            fullName = fullName,
                            purposeOfTravel = purposeOfTravel,
                            intendedTravelDate = intendedTravelDate,
                            intendedDuration = intendedDuration,
                            occupation = occupation,
                            employer = employer,
                            employmentDuration = employmentDuration,
                            incomeRange = incomeRange,
                            sponsorType = sponsorType,
                            internationalTravelHistory = internationalTravelHistory,
                            usVisaHistory = usVisaHistory,
                            usFamilyInfo = usFamilyInfo,
                            accommodationDetails = accommodationDetails
                        )
                    )
                },
                loading = isSaving
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
