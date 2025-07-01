package com.nakersolutionid.nakersolutionid

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nakersolutionid.nakersolutionid.features.settings.SettingsViewModel
import com.nakersolutionid.nakersolutionid.ui.navigation.NavigationRoot
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import com.nakersolutionid.nakersolutionid.utils.ThemeState.DARK
import com.nakersolutionid.nakersolutionid.utils.ThemeState.LIGHT
import com.nakersolutionid.nakersolutionid.utils.ThemeState.SYSTEM
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        // This flag will tell the splash screen when to dismiss.
        var isThemeReady = false

        // 1. Set up the splash screen to wait for our condition.
        installSplashScreen().setKeepOnScreenCondition {
            !isThemeReady // Keep showing splash screen until this is false
        }

        lifecycleScope.launch {
            val theme = viewModel.getCurrentTheme()
            AppCompatDelegate.setDefaultNightMode(
                when (theme) {
                    LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    DARK -> AppCompatDelegate.MODE_NIGHT_YES
                    SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
            isThemeReady = true
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val theme = when (uiState.currentTheme) {
                LIGHT -> false
                DARK -> true
                SYSTEM -> null
            }
            Log.i("Main Activity", "Dark Mode on Preferences: $theme")
            NakersolutionidTheme(darkTheme = theme ?: isSystemInDarkTheme()) {
                NavigationRoot()
            }
        }
    }
}