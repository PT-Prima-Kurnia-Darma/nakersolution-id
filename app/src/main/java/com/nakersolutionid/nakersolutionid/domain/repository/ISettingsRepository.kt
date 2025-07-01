package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.flow.StateFlow

interface ISettingsRepository {
    val currentTheme: StateFlow<ThemeState>
    suspend fun changeTheme(theme: ThemeState)
    suspend fun getCurrentTheme(): ThemeState
}