package com.example.mapsapplication.presentation.screen.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = DeepNavy,
    onPrimary = SurfaceWhite,
    secondary = DynamicCyan,
    onSecondary = SurfaceWhite,
    tertiary = EmeraldGreen,
    background = LightBackground,
    onBackground = DeepNavy,
    surface = SurfaceWhite,
    onSurface = DeepNavy,
    error = ErrorRed
)

@Composable
fun MapsApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}