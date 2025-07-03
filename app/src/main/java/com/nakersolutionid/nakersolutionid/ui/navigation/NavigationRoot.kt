package com.nakersolutionid.nakersolutionid.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
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
import com.nakersolutionid.nakersolutionid.features.report.elevator.ElevatorScreen
import com.nakersolutionid.nakersolutionid.features.settings.SettingsScreen
import com.nakersolutionid.nakersolutionid.features.signup.SignUpScreen
import com.nakersolutionid.nakersolutionid.ui.components.MenuItem
import kotlinx.serialization.Serializable

@Serializable data object Login : NavKey
@Serializable data object SignUp : NavKey
@Serializable data object Home : NavKey
@Serializable data object Report : NavKey
@Serializable data object Settings : NavKey
@Serializable data class Elevator(val menuTitle: String) : NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean
) {
    val initialScreen = if (isLoggedIn) Home else Login
    val backStack = rememberNavBackStack(initialScreen)
    val animationSpecInt = tween<IntOffset>(durationMillis = 350) // Consistent animation duration
    val animationSpecFloat = tween<Float>(durationMillis = 350) // Consistent animation duration

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
                ReportScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onMenuTypeClick = { menu ->
                        when (menu.id) {
                            6 -> backStack.add(Elevator(menu.title))
                            else -> {}
                        }
                    }
                )
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
            entry<Elevator> { navKey ->
                val title = navKey.menuTitle
                ElevatorScreen(menuTitle = title, onBackClick = { backStack.removeLastOrNull() })
            }
        },
        transitionSpec = {
            // New screen slides in from the right and fades in
            val enter = slideInHorizontally(animationSpecInt) { it } +
                    fadeIn(animationSpecFloat)

            // Old screen scales out slightly and fades out
            val exit = scaleOut(animationSpecFloat, targetScale = 0.95f) +
                    fadeOut(animationSpecFloat)

            enter togetherWith exit
        },
        popTransitionSpec = {
            // Old screen (now on top) slides out to the right and fades out
            val popExit = slideOutHorizontally(animationSpecInt) { it } +
                    fadeOut(animationSpecFloat)

            // New screen (that was underneath) scales in and fades in
            val popEnter = scaleIn(animationSpecFloat, initialScale = 0.95f) +
                    fadeIn(animationSpecFloat)

            popEnter togetherWith popExit
        },
        // For predictive back, we can use the same animation as a standard pop
        // The framework will handle scrubbing through the animation as the user gestures.
        predictivePopTransitionSpec = {
            val popExit = slideOutHorizontally(animationSpecInt) { it } +
                    fadeOut(animationSpecFloat)

            val popEnter = scaleIn(animationSpecFloat, initialScale = 0.95f) +
                    fadeIn(animationSpecFloat)

            popEnter togetherWith popExit
        },
    )
}