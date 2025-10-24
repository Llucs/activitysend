package com.llucs.activitysend.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.llucs.activitysend.data.AppPreferences
import io.github.m3extensive.extensiveColorScheme

@Composable
fun ActivitySendTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val appPreferences = remember { AppPreferences(context) }

    // Lê o estado do tema AMOLED
    val amoledThemeEnabled by appPreferences.amoledThemeEnabled.collectAsState(initial = false)

    // Escolhe o esquema de cores correto
    val colorScheme = if (amoledThemeEnabled) {
        amoledColorScheme // Seu tema AMOLED totalmente preto
    } else {
        extensiveColorScheme() // Material 3 Expressive padrão
    }

    // Aplica o tema
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // seu conjunto de tipografias
        content = content
    )
}