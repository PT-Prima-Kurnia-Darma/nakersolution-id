package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.domain.repository.ISettingsRepository
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeSettingsRepository : ISettingsRepository {
    override val currentTheme: StateFlow<ThemeState> = MutableStateFlow(ThemeState.SYSTEM)
    override suspend fun changeTheme(theme: ThemeState) {}
    override suspend fun getCurrentTheme(): ThemeState = ThemeState.SYSTEM
}