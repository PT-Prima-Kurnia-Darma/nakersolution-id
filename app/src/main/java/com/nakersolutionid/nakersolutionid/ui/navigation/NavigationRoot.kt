package com.nakersolutionid.nakersolutionid.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.nakersolutionid.nakersolutionid.features.login.LoginScreen
import com.nakersolutionid.nakersolutionid.features.signup.SignUpScreen

data object Login
data object SignUp

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
                    LoginScreen(onSignUpClick = {
                        backStack.add(SignUp)
                    })
                }

                is SignUp -> NavEntry(key) {
                    SignUpScreen(onLoginClick = {
                        backStack.removeLastOrNull()
                    })
                }

                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}