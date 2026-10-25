package com.example.baitap_tuan_4_uthtodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Ép toàn bộ app dùng màu Xanh chủ đạo #2F5BEA
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2F5BEA), // Màu xanh nút bấm
    onPrimary = Color.White,     // Chữ trên nút xanh
    primaryContainer = Color(0xFF2F5BEA),
    onPrimaryContainer = Color.White,
    background = Color.White,
    onBackground = Color(0xFF1C1C1E),
    surface = Color.White,
    onSurface = Color(0xFF1C1C1E),
    surfaceVariant = Color(0xFFF0F4FF), // Nền xám nhạt cho ô input/card
    onSurfaceVariant = Color(0xFF1C1C1E)
)

@Composable
fun Baitap_tuan_4_uthtodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Luôn dùng Light Theme để giống ảnh mockup
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}