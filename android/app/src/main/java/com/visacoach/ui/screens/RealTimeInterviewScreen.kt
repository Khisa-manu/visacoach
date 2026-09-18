package com.visacoach.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.data.remote.AndroidInterviewState
import com.visacoach.ui.theme.*
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
            onInterviewCompleted(uiState.interviewCompletedId ?: "completed")
        }
    }

    val stateColor by animateColorAsState(
        targetValue = when (uiState.state) {
            AndroidInterviewState.AI_SPEAKING -> InterviewSpeaking
            AndroidInterviewState.LISTENING -> InterviewListening
            AndroidInterviewState.PROCESSING -> InterviewProcessing
            else -> Neutral500
        },
        label = "stateColor"
    )

    val stateText = when (uiState.state) {
        AndroidInterviewState.AI_SPEAKING -> "AI SPEAKING"
        AndroidInterviewState.LISTENING -> "LISTENING TO YOU"
        AndroidInterviewState.PROCESSING -> "PROCESSING"
        AndroidInterviewState.AI_THINKING -> "AI THINKING"
        else -> "READY"
    }

    val minutes = uiState.elapsedTimeSeconds / 60
    val seconds = uiState.elapsedTimeSeconds % 60
    val timeText = String.format("%02d:%02d", minutes, seconds)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral900)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "USA VisaCoach",
                    color = Color.White,
                    style = VisaCoachTypography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    color = stateColor.copy(alpha = 0.15f),
                    shape = ShapeChip
                ) {
                    Text(
                        text = stateText,
                        color = stateColor,
                        style = VisaCoachTypography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(timeText, color = Neutral400, style = VisaCoachTypography.labelMedium)

            Spacer(modifier = Modifier.height(40.dp))

            // Waveform / Visualizer placeholder
            Box(
                modifier = Modifier
                    .size(Dimens.waveformSize)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(AccentTeal.copy(alpha = 0.25f), Color.Transparent)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(AccentTeal.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    // Simple animated bars could go here later
                    Icon(
                        Icons.Default.Mic,
                        contentDescription = null,
                        tint = AccentTeal,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "CURRENT AI QUESTION",
                color = Neutral500,
                style = VisaCoachTypography.labelSmall,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = uiState.currentQuestion.ifEmpty { "What is the purpose of your trip to the United States?" },
                color = Color.White,
                style = VisaCoachTypography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Mic Button
            Box(
                modifier = Modifier
                    .size(Dimens.micButtonSize)
                    .clip(CircleShape)
                    .background(AccentTeal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Mic,
                    contentDescription = "Microphone",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                if (uiState.state == AndroidInterviewState.LISTENING) "Listening..." else "Tap to speak",
                color = Neutral400,
                style = VisaCoachTypography.labelMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // End Interview
            TextButton(
                onClick = onClose,
                colors = ButtonDefaults.textButtonColors(contentColor = Error)
            ) {
                Icon(Icons.Default.CallEnd, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("End Interview", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}