package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.flow.StateFlow

interface SettingsUseCase {
    val currentTheme: StateFlow<ThemeState>
    suspend fun changeTheme(theme: ThemeState)
    suspend fun getCurrentTheme(): ThemeState
}