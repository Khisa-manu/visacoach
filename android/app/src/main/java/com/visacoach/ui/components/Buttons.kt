package com.visacoach.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.visacoach.ui.theme.*

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = modifier.fillMaxWidth().height(Dimens.buttonHeight),
        shape = ShapeButton,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryNavy,
            contentColor = Color.White,
            disabledContainerColor = Neutral300
        )
    ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.dp)
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                leadingIcon?.let {
                    it()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text, style = VisaCoachTypography.labelLarge, fontWeight = FontWeight.SemiBold)
                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    it()
                }
            }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(Dimens.buttonHeight),
        shape = ShapeButton,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryNavy),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            leadingIcon?.let {
                it()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text, style = VisaCoachTypography.labelLarge, fontWeight = FontWeight.SemiBold)
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(8.dp))
                it()
            }
        }
    }
}

@Composable
fun SafaricomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    subtext: String? = null
) {
    Button(
        onClick = onClick,
        enabled = !loading,
        modifier = modifier.fillMaxWidth().height(if (subtext != null) 58.dp else Dimens.buttonHeight),
        shape = ShapeButton,
        colors = ButtonDefaults.buttonColors(containerColor = SafaricomGreen, contentColor = Color.White)
    ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.dp)
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text, style = VisaCoachTypography.labelLarge, fontWeight = FontWeight.Bold)
                if (subtext != null) {
                    Text(
                        text = subtext,
                        style = VisaCoachTypography.labelSmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

@Composable
fun VisaTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = PrimaryNavy
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = VisaCoachTypography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
fun VisaMicRecordButton(
    isRecording: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isRecording) Color(0xFFEF4444) else PrimaryNavy
    Box(
        modifier = modifier
            .size(Dimens.micButtonSize)
            .clip(CircleShape)
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
            contentDescription = if (isRecording) "Stop Recording" else "Record Answer",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
    }
}