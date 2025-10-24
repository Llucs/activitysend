package com.llucs.activitysend.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AppPreferences(private val context: Context) {

    companion object {
        val ROOT_MODE_KEY = booleanPreferencesKey("root_mode_enabled")
        val SHIZUKU_MODE_KEY = booleanPreferencesKey("shizuku_mode_enabled")
        val AMOLED_THEME_KEY = booleanPreferencesKey("amoled_theme_enabled")
    }

    val rootModeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ROOT_MODE_KEY] ?: false
        }

    suspend fun setRootModeEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[ROOT_MODE_KEY] = enabled
            // Desativa Shizuku se Root for ativado
            if (enabled) {
                settings[SHIZUKU_MODE_KEY] = false
            }
        }
    }

    val shizukuModeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHIZUKU_MODE_KEY] ?: false
        }

    suspend fun setShizukuModeEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[SHIZUKU_MODE_KEY] = enabled
            // Desativa Root se Shizuku for ativado
            if (enabled) {
                settings[ROOT_MODE_KEY] = false
            }
        }
    }

    val amoledThemeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[AMOLED_THEME_KEY] ?: false
        }

    suspend fun setAmoledThemeEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[AMOLED_THEME_KEY] = enabled
        }
    }
}

