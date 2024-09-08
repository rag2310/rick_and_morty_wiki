package com.rago.rickandmortiwiki.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00FF99),   // Verde portal
    secondary = Color(0xFFFFCC00), // Amarillo
    tertiary = Color(0xFF4B88A2)   // Azul claro
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8A2BE2),
    secondary = Color(0xFF00FF99),
    tertiary = Color(0xFF2E2E2E)
)


@Composable
fun RickAndMortiWikiTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}