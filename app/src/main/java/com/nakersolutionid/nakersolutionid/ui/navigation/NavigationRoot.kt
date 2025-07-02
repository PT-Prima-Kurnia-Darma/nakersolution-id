package com.nakersolutionid.nakersolutionid.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.nakersolutionid.nakersolutionid.features.home.HomeScreen
import com.nakersolutionid.nakersolutionid.features.login.LoginScreen
import com.nakersolutionid.nakersolutionid.features.report.ReportScreen
import com.nakersolutionid.nakersolutionid.features.settings.SettingsScreen
import com.nakersolutionid.nakersolutionid.features.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable data object Login : NavKey
@Serializable data object SignUp : NavKey
@Serializable data object Home : NavKey
@Serializable data object Report : NavKey
@Serializable data object Settings : NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean
) {
    val initialScreen = if (isLoggedIn) Home else Login
    val backStack = rememberNavBackStack(initialScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Login> {
                LoginScreen(
                    onSignUpClick = { backStack.add(SignUp) },
                    onLoginClick = {
                        backStack.clear()
                        backStack.add(Home)
//                        backStack.removeFirstOrNull()
                    }
                )
            }
            entry<SignUp> {
                SignUpScreen(onLoginClick = {
                    backStack.removeLastOrNull()
                })
            }
            entry<Home> {
                HomeScreen(
                    onLogoutClick = {
                        backStack.clear()
                        backStack.add(Login)
//                        backStack.removeFirstOrNull()
                    },
                    onMenuItemClick = { item ->
                        when (item) {
                            1 -> {
                                backStack.add(Report)
                            }
                            7 -> {
                                backStack.add(Settings)
                            }
                            else -> {}
                        }
                    }
                )
            }
            entry<Report> {
                ReportScreen()
            }
            entry<Settings> {
                SettingsScreen(
                    onBackClick = {
                        backStack.removeLastOrNull()
                    },
                    onLogoutClick = {
                        backStack.clear()
                        backStack.add(Login)
//                        backStack.removeRange(0, backStack.size - 1)
                    }
                )
            }
        }
    )
}