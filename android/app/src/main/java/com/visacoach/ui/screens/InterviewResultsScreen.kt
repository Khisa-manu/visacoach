package com.visacoach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.visacoach.ui.components.PrimaryButton
import com.visacoach.ui.components.SecondaryButton
import com.visacoach.ui.components.VisaCard
import com.visacoach.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewResultsScreen(
    overallScore: Float = 8.4f,
    onPracticeWeakAreas: () -> Unit,
    onShare: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = Neutral50,
        topBar = {
            TopAppBar(
                title = { Text("Interview Results", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onShare) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.screenHorizontal),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Score Header
                VisaCard(containerColor = PrimaryNavy) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Great Job!",
                            style = VisaCoachTypography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            String.format("%.1f", overallScore),
                            style = VisaCoachTypography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = AccentTeal
                        )
                        Text(
                            "/ 10  •  Strong",
                            style = VisaCoachTypography.bodyMedium,
                            color = Neutral300
                        )
                    }
                }
            }

            item {
                Text(
                    "Overall Breakdown",
                    style = VisaCoachTypography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ScoreItem("Content", 8.7f, Modifier.weight(1f))
                    ScoreItem("Fluency", 8.1f, Modifier.weight(1f))
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ScoreItem("Confidence", 8.5f, Modifier.weight(1f))
                    ScoreItem("Consistency", 7.9f, Modifier.weight(1f))
                }
            }

            item {
                VisaCard {
                    Text("Key Strengths", style = VisaCoachTypography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    listOf(
                        "Clear and relevant responses",
                        "Good use of examples and details",
                        "Confident tone"
                    ).forEach {
                        Text("• $it", style = VisaCoachTypography.bodyMedium, color = Neutral700)
                    }
                }
            }

            item {
                VisaCard {
                    Text("Areas to Improve", style = VisaCoachTypography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    listOf(
                        "Provide more specific examples",
                        "Improve speaking pace and fluency",
                        "Reduce filler words"
                    ).forEach {
                        Text("• $it", style = VisaCoachTypography.bodyMedium, color = Neutral700)
                    }
                }
            }

            item {
                PrimaryButton(text = "Practice Weak Areas", onClick = onPracticeWeakAreas)
                Spacer(modifier = Modifier.height(12.dp))
                SecondaryButton(text = "Share Results", onClick = onShare)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun ScoreItem(label: String, score: Float, modifier: Modifier = Modifier) {
    VisaCard(modifier = modifier) {
        Text(label, style = VisaCoachTypography.labelMedium, color = Neutral600)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            String.format("%.1f", score),
            style = VisaCoachTypography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryNavy
        )
        LinearProgressIndicator(
            progress = { score / 10f },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            color = AccentTeal,
            trackColor = Neutral200
        )
    }
}