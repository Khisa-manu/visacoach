package com.visacoach.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.data.remote.AndroidInterviewState
import com.visacoach.ui.theme.PrimaryNavy
import com.visacoach.ui.viewmodels.InterviewViewModel

@Composable
fun RealTimeInterviewScreen(
    viewModel: InterviewViewModel,
    onInterviewCompleted: (String) -> Unit,
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startInterviewSession()
    }

    LaunchedEffect(uiState.state) {
        if (uiState.state == AndroidInterviewState.COMPLETED) {
            onInterviewCompleted(uiState.interviewCompletedId ?: "completed-session")
        }
    }

    val stateBadgeColor by animateColorAsState(
        targetValue = when (uiState.state) {
            AndroidInterviewState.AI_SPEAKING -> Color(0xFF2563EB)
            AndroidInterviewState.LISTENING -> Color(0xFF059669)
            AndroidInterviewState.PROCESSING -> Color(0xFFD97706)
            AndroidInterviewState.AI_THINKING -> Color(0xFF7C3AED)
            AndroidInterviewState.ERROR -> Color(0xFFDC2626)
            else -> Color(0xFF64748B)
        },
        label = "stateBadgeColor"
    )

    val stateBadgeText = when (uiState.state) {
        AndroidInterviewState.AI_SPEAKING -> "AI SPEAKING"
        AndroidInterviewState.LISTENING -> "LISTENING TO YOU"
        AndroidInterviewState.PROCESSING -> "PROCESSING AUDIO"
        AndroidInterviewState.AI_THINKING -> "AI THINKING..."
        AndroidInterviewState.CONNECTING -> "CONNECTING..."
        AndroidInterviewState.COMPLETED -> "INTERVIEW FINISHED"
        AndroidInterviewState.ERROR -> "CONNECTION ERROR"
        else -> "READY"
    }

    val minutes = uiState.elapsedTimeSeconds / 60
    val seconds = uiState.elapsedTimeSeconds % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Mock Consular Interview",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                    Text(
                        uiState.connectionStatus,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = formattedTime,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                    }
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Done Speaking / Push to Send Button
                if (uiState.state == AndroidInterviewState.LISTENING) {
                    Button(
                        onClick = { viewModel.onUserDoneSpeaking() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        Icon(Icons.Default.Done, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Done Speaking (Submit Answer)", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                OutlinedButton(
                    onClick = {
                        viewModel.endInterview()
                        onClose()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626)),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Icon(Icons.Default.CallEnd, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("End Interview Session")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Indicators
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 10.dp)) {
                // State Badge
                Box(
                    modifier = Modifier
                        .background(stateBadgeColor.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = stateBadgeText,
                        color = stateBadgeColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Question ${uiState.questionNumber} of ~${uiState.totalQuestionsEstimated}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Category: ${uiState.category.replace("_", " ")}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryNavy
                )
            }

            // Central Area: AI Question Prompt & Waveform
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "\"${uiState.currentQuestionText}\"",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp,
                            color = PrimaryNavy
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Audio Waveform Visualizer
                Row(
                    modifier = Modifier.height(56.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    uiState.audioAmplitudes.forEach { amp ->
                        val barHeight = (amp * 48).coerceIn(8f, 52f)
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .height(barHeight.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(stateBadgeColor)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Microphone Status Circle
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .background(
                            if (uiState.isMicActive) Color(0xFF059669).copy(alpha = 0.2f) else Color(0xFFE2E8F0),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (uiState.isMicActive) Icons.Default.Mic else Icons.Default.MicOff,
                        contentDescription = null,
                        tint = if (uiState.isMicActive) Color(0xFF059669) else Color(0xFF64748B),
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Transcript Display
                if (uiState.userTranscript.isNotBlank()) {
                    Text(
                        text = uiState.userTranscript,
                        fontSize = 13.sp,
                        color = Color(0xFF334155),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}
