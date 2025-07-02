package com.nakersolutionid.nakersolutionid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.features.settings.SettingsViewModel
import com.nakersolutionid.nakersolutionid.ui.navigation.NavigationRoot
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import com.nakersolutionid.nakersolutionid.utils.ThemeState.DARK
import com.nakersolutionid.nakersolutionid.utils.ThemeState.LIGHT
import com.nakersolutionid.nakersolutionid.utils.ThemeState.SYSTEM
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        // This flag will tell the splash screen when to dismiss.
        var isThemeReady = false
        var isLoggedInOuter: Boolean? = null

        // 1. Set up the splash screen to wait for our condition.
        installSplashScreen().setKeepOnScreenCondition {
            !isThemeReady || isLoggedInOuter == null
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val theme = when (uiState.currentTheme) {
                LIGHT -> {
                    isThemeReady = true
                    false
                }
                DARK -> {
                    isThemeReady = true
                    true
                }
                SYSTEM -> {
                    isThemeReady = true
                    null
                }
                null -> {
                    isThemeReady = false
                    null
                }
            }

            val isLoggedIn = uiState.isLoggedIn
            isLoggedInOuter = when (uiState.isLoggedIn) {
                true -> true
                false -> false
                null -> null
            }

            NakersolutionidTheme(darkTheme = theme ?: isSystemInDarkTheme()) {
                isLoggedIn?.let {
                    NavigationRoot(isLoggedIn = isLoggedIn)
                }
            }
        }
    }
}