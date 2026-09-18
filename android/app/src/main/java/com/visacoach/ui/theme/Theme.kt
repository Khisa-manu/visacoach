package com.visacoach.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryNavy,
    onPrimary = Color.White,
    primaryContainer = Neutral100,
    onPrimaryContainer = PrimaryNavy,
    secondary = AccentTeal,
    onSecondary = Color.White,
    tertiary = SafaricomGreen,
    onTertiary = Color.White,
    background = Neutral50,
    onBackground = Neutral900,
    surface = Color.White,
    onSurface = Neutral900,
    surfaceVariant = Neutral100,
    onSurfaceVariant = Neutral600,
    outline = Neutral300,
    error = Error,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF60A5FA),
    onPrimary = Neutral900,
    secondary = Color(0xFF2DD4BF),
    onSecondary = Neutral900,
    tertiary = Color(0xFF4ADE80),
    background = Neutral900,
    onBackground = Neutral50,
    surface = Neutral800,
    onSurface = Neutral50,
    surfaceVariant = Neutral700,
    onSurfaceVariant = Neutral300,
    outline = Neutral600,
    error = Color(0xFFF87171),
    onError = Neutral900
)

object VisaCoachColorTokens {
    val primaryNavy = PrimaryNavy
    val accentTeal = AccentTeal
    val safaricomGreen = SafaricomGreen
    val textPrimary = PrimaryNavy
    val textSecondary = Neutral600
    val textTertiary = Neutral400
    val cardBorder = Neutral200
    val error = Error
    val success = Success
    val warning = Warning
    val info = Info
    val micRecording = Color(0xFFEF4444)
    val safaricomGreenContainer = Color(0xFFDCFCE7)
    val onSafaricomGreenContainer = SafaricomGreen
}

object VisaCoachTheme {
    val colors = VisaCoachColorTokens
    val typography = VisaCoachTypography
    val dimens = Dimens
    val shapes = VisaCoachShapeTokens

    val materialColors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = VisaCoachTypography,
            shapes = VisaCoachShapes,
            content = content
        )
    }
}