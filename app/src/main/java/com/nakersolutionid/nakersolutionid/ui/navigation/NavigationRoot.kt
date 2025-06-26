package com.nakersolutionid.nakersolutionid.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.nakersolutionid.nakersolutionid.features.home.HomeScreen
import com.nakersolutionid.nakersolutionid.features.login.LoginScreen
import com.nakersolutionid.nakersolutionid.features.signup.SignUpScreen

data object Login
data object SignUp
data object Home

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<Any>(Login) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
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

                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}