package com.ecorecover.app.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = EcoGreenLight,
    onPrimary = OnEcoGreenLight,
    primaryContainer = EcoGreenContainerLight,
    onPrimaryContainer = OnEcoGreenContainerLight,
    secondary = BlueLight,
    onSecondary = OnBlueLight,
    secondaryContainer = BlueContainerLight,
    onSecondaryContainer = OnBlueContainerLight,
    tertiary = AmberLight,
    onTertiary = OnAmberLight,
    tertiaryContainer = AmberContainerLight,
    onTertiaryContainer = OnAmberContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight
)

private val DarkColors = darkColorScheme(
    primary = EcoGreenDark,
    onPrimary = OnEcoGreenDark,
    primaryContainer = EcoGreenContainerDark,
    onPrimaryContainer = OnEcoGreenContainerDark,
    secondary = BlueDark,
    onSecondary = OnBlueDark,
    secondaryContainer = BlueContainerDark,
    onSecondaryContainer = OnBlueContainerDark,
    tertiary = AmberDark,
    onTertiary = OnAmberDark,
    tertiaryContainer = AmberContainerDark,
    onTertiaryContainer = OnAmberContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark
)

@Composable
fun EcoRecoverTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}