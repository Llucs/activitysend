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
    val amoledThemeEnabled by appPreferences.amoledThemeEnabled.collectAsState(initial = false)
    
    val colorScheme = if (amoledThemeEnabled) {
        amoledColorScheme
    } else {
        extensiveColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
