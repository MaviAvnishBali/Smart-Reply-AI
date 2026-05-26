package com.myai.smartreplyai.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = WhatsAppDarkGreen,
    onPrimary = CardLight,
    secondary = WhatsAppGreen,
    onSecondary = CardLight,
    tertiary = WhatsAppTeal,
    background = SurfaceLight,
    onBackground = CardDark,
    surface = CardLight,
    onSurface = CardDark,
    surfaceVariant = SurfaceLight,
    error = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = WhatsAppGreen,
    onPrimary = CardDark,
    secondary = WhatsAppDarkGreen,
    onSecondary = CardLight,
    tertiary = WhatsAppTeal,
    background = SurfaceDark,
    onBackground = CardLight,
    surface = CardDark,
    onSurface = CardLight,
    surfaceVariant = SurfaceDark,
    error = ErrorRed
)

@Composable
fun SmartReplyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SmartReplyTypography,
        content = content
    )
}
