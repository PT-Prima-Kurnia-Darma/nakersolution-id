package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.domain.repository.ISettingsRepository
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.flow.StateFlow

class SettingsInteraction(private val settingsRepository: ISettingsRepository) : SettingsUseCase {
    override val currentTheme: StateFlow<ThemeState> = settingsRepository.currentTheme
    override suspend fun changeTheme(theme: ThemeState) = settingsRepository.changeTheme(theme)
    override suspend fun getCurrentTheme(): ThemeState = settingsRepository.getCurrentTheme()
}