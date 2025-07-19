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
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.features.bap.BAPCreationScreen
import com.nakersolutionid.nakersolutionid.features.bap.BAPScreen
import com.nakersolutionid.nakersolutionid.features.history.HistoryScreen
import com.nakersolutionid.nakersolutionid.features.home.HomeScreen
import com.nakersolutionid.nakersolutionid.features.login.LoginScreen
import com.nakersolutionid.nakersolutionid.features.report.ReportScreen
import com.nakersolutionid.nakersolutionid.features.report.ee.EEScreen
import com.nakersolutionid.nakersolutionid.features.report.ilpp.ILPPScreen
import com.nakersolutionid.nakersolutionid.features.report.ipk.IPKScreen
import com.nakersolutionid.nakersolutionid.features.report.paa.PAAScreen
import com.nakersolutionid.nakersolutionid.features.report.ptp.PTPScreen
import com.nakersolutionid.nakersolutionid.features.report.pubt.PUBTScreen
import com.nakersolutionid.nakersolutionid.features.settings.SettingsScreen
import com.nakersolutionid.nakersolutionid.features.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable data object Login : NavKey
@Serializable data object SignUp : NavKey
@Serializable data object Home : NavKey
@Serializable data object Report : NavKey
@Serializable data object BAP : NavKey
@Serializable data class BAPCreation(val id: Long, val subInspectionType: SubInspectionType) : NavKey
@Serializable data object History : NavKey
@Serializable data object Settings : NavKey

@Serializable data object EE : NavKey
@Serializable data object PAA : NavKey
@Serializable data object ILPP : NavKey
@Serializable data object PTP : NavKey
@Serializable data object IPK : NavKey
@Serializable data object PUBT : NavKey

// Edit routes for existing reports
@Serializable data class EEEdit(val reportId: Long) : NavKey
@Serializable data class PAAEdit(val reportId: Long) : NavKey
@Serializable data class ILPPEdit(val reportId: Long) : NavKey
@Serializable data class PTPEdit(val reportId: Long) : NavKey
@Serializable data class IPKEdit(val reportId: Long) : NavKey
@Serializable data class PUBTEdit(val reportId: Long) : NavKey

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
                    }
                )
            }
            entry<SignUp> { SignUpScreen(onLoginClick = { backStack.removeLastOrNull() }) }
            entry<Home> {
                HomeScreen(
                    onLogoutClick = {
                        backStack.clear()
                        backStack.add(Login)
                    },
                    onMenuItemClick = { item ->
                        when (item) {
                            1 -> { backStack.add(Report) }
                            2 -> { backStack.add(BAP) }
                            7 -> { backStack.add(Settings) }
                            6 -> { backStack.add(History) }
                            else -> {}
                        }
                    }
                )
            }
            entry<Report> {
                ReportScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onMenuTypeClick = { menu ->
                        when (menu) {
                            1 -> backStack.add(ILPP)
                            2 -> backStack.add(IPK)
                            3 -> backStack.add(PAA)
                            4 -> backStack.add(PUBT)
                            5 -> backStack.add(PTP)
                            6 -> backStack.add(EE)
                            else -> {}
                        }
                    }
                )
            }

            entry<Settings> {
                SettingsScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onLogoutClick = {
                        backStack.clear()
                        backStack.add(Login)
                    }
                )
            }
            entry<History> { 
                HistoryScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onEditClick = { history ->
                        when (history.inspectionType) {
                            InspectionType.EE -> backStack.add(EEEdit(history.id))
                            InspectionType.PAA -> backStack.add(PAAEdit(history.id))
                            InspectionType.ILPP -> backStack.add(ILPPEdit(history.id))
                            InspectionType.PTP -> backStack.add(PTPEdit(history.id))
                            InspectionType.IPK -> backStack.add(IPKEdit(history.id))
                            InspectionType.PUBT -> backStack.add(PUBTEdit(history.id))
                        }
                    }
                ) 
            }

            entry<EE> { EEScreen(onBackClick = { backStack.removeLastOrNull() }) }
            entry<PAA> { PAAScreen(onBackClick = { backStack.removeLastOrNull() }) }
            entry<ILPP> { ILPPScreen(onBackClick = { backStack.removeLastOrNull() }) }
            entry<PTP> { PTPScreen(onBackClick = { backStack.removeLastOrNull() }) }
            entry<IPK> { IPKScreen(onBackClick = { backStack.removeLastOrNull() }) }
            entry<PUBT> { PUBTScreen(onBackClick = { backStack.removeLastOrNull() }) }

            // Edit entries
            entry<EEEdit> { key -> EEScreen(reportId = key.reportId, onBackClick = { backStack.removeLastOrNull() }) }
            entry<PAAEdit> { key -> PAAScreen(reportId = key.reportId, onBackClick = { backStack.removeLastOrNull() }) }
            entry<ILPPEdit> { key -> ILPPScreen(reportId = key.reportId, onBackClick = { backStack.removeLastOrNull() }) }
            entry<PTPEdit> { key -> PTPScreen(reportId = key.reportId, onBackClick = { backStack.removeLastOrNull() }) }
            entry<IPKEdit> { key -> IPKScreen(reportId = key.reportId, onBackClick = { backStack.removeLastOrNull() }) }
            entry<PUBTEdit> { key -> PUBTScreen(reportId = key.reportId, onBackClick = { backStack.removeLastOrNull() }) }

            entry<BAP> {
                BAPScreen(
                    onBackClick = { backStack.removeLastOrNull() },
                    onItemClick = { id, subInspectionType -> backStack.add(BAPCreation(id, subInspectionType)) })
            }
            entry<BAPCreation> { key ->
                BAPCreationScreen(id = key.id, subInspectionType = key.subInspectionType, onBackClick = { backStack.removeLastOrNull() })
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