package com.nakersolutionid.nakersolutionid.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nakersolutionid.nakersolutionid.data.preference.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

class UserPreference(private val context: Context) {
    private object Keys {
        val ID = stringPreferencesKey("user_id")
        val NAME = stringPreferencesKey("user_name")
        val USERNAME = stringPreferencesKey("user_username")
        val TOKEN = stringPreferencesKey("user_token")
    }

    suspend fun saveUser(user: UserModel) {
        context.dataStore.edit { preferences ->
            preferences[Keys.ID] = user.id
            preferences[Keys.NAME] = user.name
            preferences[Keys.USERNAME] = user.username
            preferences[Keys.TOKEN] = user.token
        }
    }

    fun getUser(): Flow<UserModel> {
        return context.dataStore.data.map { preferences ->
            UserModel(
                preferences[Keys.ID] ?: "",
                preferences[Keys.NAME] ?: "",
                preferences[Keys.USERNAME] ?: "",
                preferences[Keys.TOKEN] ?: ""
            )
        }
    }

    // ADD THIS: Perfect for one-time, non-observing reads
    suspend fun getUserToken(): String? {
        // .first() is a terminal operator that gets the first emitted value
        // from the flow and then cancels the flow's collection.
        val preferences = context.dataStore.data.first()
        return preferences[Keys.TOKEN]
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences[Keys.ID] = ""
            preferences[Keys.NAME] = ""
            preferences[Keys.USERNAME] = ""
            preferences[Keys.TOKEN] = ""
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
    suspend fun <T> updatePreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
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
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }
}