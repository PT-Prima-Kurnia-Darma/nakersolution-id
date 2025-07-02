package com.nakersolutionid.nakersolutionid.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsPreference(private val context: Context) {
    object Keys {
        val THEME = stringPreferencesKey("settings_theme")
    }

    suspend fun saveTheme(theme: ThemeState) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.THEME] = theme.name
        }
    }

    suspend fun getCurrentTheme(): ThemeState {
        val preferences = context.settingsDataStore.data.first()
        val themeName = preferences[Keys.THEME] ?: ThemeState.SYSTEM.name
        return try {
            ThemeState.valueOf(themeName)
        } catch (e: IllegalArgumentException) {
            ThemeState.SYSTEM
        }
    }

    val currentTheme: Flow<ThemeState> = context.settingsDataStore.data.map { preferences ->
        // Get the string value from preferences, defaulting to SYSTEM if not set
        val themeName = preferences[Keys.THEME] ?: ThemeState.SYSTEM.name

        // Convert the string name back to the enum, falling back to SYSTEM on error
        try {
            ThemeState.valueOf(themeName)
        } catch (e: IllegalArgumentException) {
            // This can happen if the saved string doesn't match any enum constant
            ThemeState.SYSTEM
        }
    }

    /**
     * Updates a single preference value in the DataStore generically.
     * This function allows you to update any key with a matching value type.
     *
     * @param key The Preferences.Key<T> you want to update.
     * @param value The new value of type T for the given key.
     *
     */
    suspend fun <T> setPreference(key: Preferences.Key<T>, value: T) {
        context.settingsDataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * Retrieves a single preference value from the DataStore generically as a Flow.
     * This is useful for observing changes to a specific preference.
     *
     * @param key The Preferences.Key<T> you want to retrieve.
     * @param defaultValue The default value to return if the key does not exist.
     * @return A Flow emitting the preference value of type T.
     *
     */
    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.settingsDataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    suspend fun <T> deletePreference(pref: Preferences.Key<T>) {
        context.settingsDataStore.edit { preferences ->
            preferences.remove(pref)
        }
    }
}
