package com.nakersolutionid.nakersolutionid.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nakersolutionid.nakersolutionid.data.preference.model.UserModel
import kotlinx.coroutines.flow.Flow
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

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}