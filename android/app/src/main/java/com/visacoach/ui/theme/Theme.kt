package com.visacoach.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Professional palette for USA VisaCoach
val PrimaryNavy = Color(0xFF0A2540)
val AccentTeal = Color(0xFF00A699)
val SafaricomGreen = Color(0xFF008938)
val DarkBackground = Color(0xFF0F172A)
val SurfaceLight = Color(0xFFF8FAFC)
val SurfaceCard = Color(0xFFFFFFFF)
val TextDark = Color(0xFF0F172A)
val TextMuted = Color(0xFF64748B)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryNavy,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE2E8F0),
    secondary = AccentTeal,
    onSecondary = Color.White,
    tertiary = SafaricomGreen,
    background = SurfaceLight,
    surface = SurfaceCard,
    onBackground = TextDark,
    onSurface = TextDark
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF60A5FA),
    onPrimary = Color(0xFF0F172A),
    primaryContainer = Color(0xFF1E293B),
    secondary = Color(0xFF2DD4BF),
    onSecondary = Color(0xFF0F172A),
    tertiary = Color(0xFF4ADE80),
    background = DarkBackground,
    surface = Color(0xFF1E293B),
    onBackground = Color(0xFFF8FAFC),
    onSurface = Color(0xFFF8FAFC)
)

@Composable
fun VisaCoachTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}
