package com.example.baitap_tuan_4_uthtodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2F5BEA),
    onPrimary = Color.White,
    background = Color.White,
    surface = Color.White,
    onBackground = Color(0xFF1C1C1E),
    onSurface = Color(0xFF1C1C1E)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF2F5BEA),
    onPrimary = Color.White,
    background = Color(0xFF1C1C1E),
    surface = Color(0xFF1C1C1E),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun Baitap_tuan_4_uthtodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}