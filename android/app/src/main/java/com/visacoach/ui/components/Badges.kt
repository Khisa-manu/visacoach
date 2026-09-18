package com.visacoach.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.visacoach.ui.theme.*

enum class BadgeType {
    SUCCESS, WARNING, ERROR, INFO, NAVY, SAFARICOM
}

@Composable
fun VisaStatusBadge(
    text: String,
    type: BadgeType = BadgeType.INFO,
    modifier: Modifier = Modifier
) {
    val (bg, textColor) = when (type) {
        BadgeType.SUCCESS -> Pair(Success.copy(alpha = 0.12f), Success)
        BadgeType.WARNING -> Pair(Warning.copy(alpha = 0.15f), Warning)
        BadgeType.ERROR -> Pair(Error.copy(alpha = 0.12f), Error)
        BadgeType.INFO -> Pair(Info.copy(alpha = 0.12f), Info)
        BadgeType.NAVY -> Pair(PrimaryNavy.copy(alpha = 0.10f), PrimaryNavy)
        BadgeType.SAFARICOM -> Pair(SafaricomGreen.copy(alpha = 0.15f), SafaricomGreen)
    }

    Surface(
        modifier = modifier,
        shape = ShapeChip,
        color = bg
    ) {
        Text(
            text = text,
            style = VisaCoachTypography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun VisaCategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = ShapeChip,
        color = if (selected) PrimaryNavy else Color.White,
        border = if (selected) null else BorderStroke(1.dp, Neutral300)
    ) {
        Text(
            text = text,
            style = VisaCoachTypography.labelMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color.White else Neutral700,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}
