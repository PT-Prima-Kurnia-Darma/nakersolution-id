package com.nakersolutionid.nakersolutionid.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.nakersolutionid.nakersolutionid.features.home.HomeScreen
import com.nakersolutionid.nakersolutionid.features.login.LoginScreen
import com.nakersolutionid.nakersolutionid.features.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data object Login : NavKey
@Serializable
data object SignUp : NavKey
@Serializable
data object Home : NavKey

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Login)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            // Add the default decorators for managing scenes and saving state
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is Login -> NavEntry(key) {
                    LoginScreen(
                        onSignUpClick = {
                            backStack.add(SignUp)
                        },
                        onLoginClick = {
                            backStack.add(Home)
                        }
                    )
                }

                is SignUp -> NavEntry(key) {
                    SignUpScreen(onLoginClick = {
                        backStack.removeLastOrNull()
                    })
                }

                is Home -> NavEntry(key) {
                    HomeScreen()
                }

                else -> NavEntry(key) { Text("Unknown route") }
            }
        }
    )
}