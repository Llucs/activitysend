
package com.llucs.activitysend.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.llucs.activitysend.data.AppPreferences
import kotlinx.coroutines.launch
import com.llucs.activitysend.R
import com.llucs.activitysend.core.LocaleHelper
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val appPreferences = remember { AppPreferences(context) }
    
    val rootModeEnabled by appPreferences.rootModeEnabled.collectAsState(initial = false)
    val shizukuModeEnabled by appPreferences.shizukuModeEnabled.collectAsState(initial = false)
    val amoledThemeEnabled by appPreferences.amoledThemeEnabled.collectAsState(initial = false)

    val currentLanguage = remember { mutableStateOf(LocaleHelper.getLanguage(context)) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.settings), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        
        // Root Mode
        Card(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                leadingContent = { Text("#", style = MaterialTheme.typography.headlineSmall) },
                headlineContent = { Text(stringResource(R.string.enable_root_mode)) },
                supportingContent = { Text(stringResource(R.string.root_mode_description)) },
                trailingContent = {
                    Switch(
                        checked = rootModeEnabled,
                        onCheckedChange = {
                            coroutineScope.launch {
                                appPreferences.setRootModeEnabled(it)
                            }
                        }
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Shizuku Mode
        Card(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                leadingContent = { Icon(Icons.Default.Code, contentDescription = null) }, // Mantendo Code como ícone para Shizuku, pois não há um ícone específico do Shizuku no Material Icons
                headlineContent = { Text(stringResource(R.string.enable_shizuku_mode)) },
                supportingContent = { Text(stringResource(R.string.shizuku_mode_description)) },
                trailingContent = {
                    Switch(
                        checked = shizukuModeEnabled,
                        onCheckedChange = {
                            coroutineScope.launch {
                                appPreferences.setShizukuModeEnabled(it)
                            }
                        }
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Amoled Theme
        Card(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                leadingContent = { Icon(Icons.Default.DarkMode, contentDescription = null) },
                headlineContent = { Text("Tema AMOLED") },
                supportingContent = { Text("Ativa o tema preto puro para telas AMOLED.") },
                trailingContent = {
                    Switch(
                        checked = amoledThemeEnabled,
                        onCheckedChange = {
                            coroutineScope.launch {
                                appPreferences.setAmoledThemeEnabled(it)
                            }
                        }
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Language selection
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = { expanded = true }
        ) {
            ListItem(
                leadingContent = { Icon(Icons.Default.Language, contentDescription = null) },
                headlineContent = { Text(stringResource(R.string.language)) },
                supportingContent = { Text(getLanguageDisplayName(currentLanguage.value)) }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                LanguageOption(languageCode = "en", onClick = { 
                    currentLanguage.value = "en"
                    LocaleHelper.setLocale(context, "en")
                    restartActivity(context)
                    expanded = false
                })
                LanguageOption(languageCode = "pt", onClick = { 
                    currentLanguage.value = "pt"
                    LocaleHelper.setLocale(context, "pt")
                    restartActivity(context)
                    expanded = false
                })
                LanguageOption(languageCode = "system", onClick = { 
                    currentLanguage.value = Locale.getDefault().language
                    LocaleHelper.setLocale(context, Locale.getDefault().language)
                    restartActivity(context)
                    expanded = false
                })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Card(onClick = { uriHandler.openUri("https://github.com/Llucs/activitysend") }) {
            ListItem(
                leadingContent = { Icon(Icons.Default.OpenInNew, contentDescription = null) }, // Usando OpenInNew para representar o link externo do Github
                headlineContent = { Text(stringResource(R.string.github_repository)) },
                supportingContent = { Text(stringResource(R.string.access_source_code)) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(stringResource(R.string.version_format, "0.1"), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun LanguageOption(languageCode: String, onClick: () -> Unit) {
    DropdownMenuItem(onClick = onClick, text = {
        Text(getLanguageDisplayName(languageCode))
    })
}

@Composable
private fun getLanguageDisplayName(languageCode: String): String {
    return when (languageCode) {
        "en" -> "English"
        "pt" -> "Português"
        else -> stringResource(R.string.system_default) // Fallback for system default or unknown
    }
}

private fun restartActivity(context: Context) {
    val activity = context.findActivity()
    activity?.let {
        it.recreate()
    }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

