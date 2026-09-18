package com.visacoach.ui.screens

import androidx.compose.foundation.background
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
import com.visacoach.domain.models.EvaluationModel
import com.visacoach.ui.theme.PrimaryNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewResultsScreen(
    evaluation: EvaluationModel? = null,
    onDone: () -> Unit,
    onPracticeWeakAreas: () -> Unit
) {
    val model = evaluation ?: EvaluationModel(
        interviewId = "mock-id",
        sessionId = "mock-session",
        completedAt = "Just now",
        overallScore = 7.7,
        scores = mapOf(
            "Relevance" to 8.0,
            "Clarity" to 8.2,
            "Consistency" to 7.5,
            "Conciseness" to 7.0,
            "Completeness" to 7.6,
            "Communication" to 8.0
        ),
        strengths = listOf(
            "Clear explanation of employment duration and professional responsibilities in Nairobi.",
            "Firmly stated intended return date and travel duration.",
            "Polite, respectful tone maintained throughout all questions."
        ),
        areasToPractice = listOf(
            "Give shorter, more direct answers regarding who is paying for lodging.",
            "Avoid introducing unsolicited secondary details unless explicitly asked.",
            "Practice articulating the exact business/vacation itinerary concisely."
        ),
        potentialInconsistencies = listOf(
            "Estimated accommodation budget was slightly lower than Manhattan standard rates."
        ),
        recommendations = listOf(
            "Practice 2-sentence responses for all financial questions.",
            "Keep employer confirmation letter details memorized.",
            "Focus on strong ties to your home country (job tenure, family, assets)."
        ),
        disclaimer = "NOTE: These scores and feedback are educational practice metrics designed to improve interview communication and coherence. They do NOT calculate or predict visa approval probability, refusal probability, or any U.S. consular decision."
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Interview Practice Report", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDone,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Dashboard")
                }
                Button(
                    onClick = onPracticeWeakAreas,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Drill Weak Areas")
                }
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
            // Overall Score Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryNavy)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "OVERALL PRACTICE SCORE",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${model.overallScore} / 10",
                            color = Color.White,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Strong communication with minor areas for conciseness refinement",
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Training Disclaimer Banner
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB))
                ) {
                    Row(modifier = Modifier.padding(14.dp)) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFB45309), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = model.disclaimer,
                            fontSize = 11.sp,
                            color = Color(0xFF92400E),
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // 6 Core Metrics Breakdown
            item {
                Text("Performance Breakdown", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    model.scores.forEach { (category, score) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(category, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                            Text("$score / 10", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryNavy)
                        }
                    }
                }
            }

            // Strengths
            item {
                Text("Observed Strengths", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF059669))
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    model.strengths.forEach { str ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(str, fontSize = 13.sp, lineHeight = 18.sp)
                        }
                    }
                }
            }

            // Areas to Practice
            item {
                Text("Areas to Practice & Tighten", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFD97706))
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    model.areasToPractice.forEach { area ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Default.ArrowRight, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(area, fontSize = 13.sp, lineHeight = 18.sp)
                        }
                    }
                }
            }

            // Potential Inconsistencies
            if (model.potentialInconsistencies.isNotEmpty()) {
                item {
                    Text("Potential Inconsistencies Flagged", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFDC2626))
                    Spacer(modifier = Modifier.height(6.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        model.potentialInconsistencies.forEach { inc ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(inc, fontSize = 13.sp, lineHeight = 18.sp)
                            }
                        }
                    }
                }
            }

            // Recommendations
            item {
                Text("Recommended Next Practice Steps", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    model.recommendations.forEach { rec ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(rec, fontSize = 13.sp, lineHeight = 18.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
