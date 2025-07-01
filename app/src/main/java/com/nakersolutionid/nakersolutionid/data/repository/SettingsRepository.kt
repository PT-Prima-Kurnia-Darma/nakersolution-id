package com.nakersolutionid.nakersolutionid.data.repository

import com.nakersolutionid.nakersolutionid.data.preference.SettingsPreference
import com.nakersolutionid.nakersolutionid.domain.repository.ISettingsRepository
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SettingsRepository(private val settingsPreference: SettingsPreference) : ISettingsRepository {
    override val currentTheme: StateFlow<ThemeState> = settingsPreference.currentTheme
        .stateIn(
            CoroutineScope(Dispatchers.IO),
            SharingStarted.WhileSubscribed(),
            ThemeState.SYSTEM
        )

    override suspend fun changeTheme(theme: ThemeState) = settingsPreference.saveTheme(theme)
    override suspend fun getCurrentTheme(): ThemeState = settingsPreference.getCurrentTheme()
}