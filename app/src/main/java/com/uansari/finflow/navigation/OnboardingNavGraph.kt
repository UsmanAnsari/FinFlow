package com.uansari.finflow.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.uansari.finflow.ui.onboarding.OnboardingViewModel
import com.uansari.finflow.ui.onboarding.screens.ChooseAccountTypeScreen
import com.uansari.finflow.ui.onboarding.screens.IdVerificationScreen
import com.uansari.finflow.ui.onboarding.screens.PersonalDetailsScreen
import com.uansari.finflow.ui.onboarding.screens.SuccessScreen
import com.uansari.finflow.ui.onboarding.screens.WelcomeScreen

private val enterTransition = slideInHorizontally(
    animationSpec = tween(750)
) { it } + fadeIn(tween(750))

private val exitTransition = slideOutHorizontally(
    animationSpec = tween(750)
) { -it } + fadeOut(tween(750))

private val popEnterTransition = slideInHorizontally(
    animationSpec = tween(750)
) { -it } + fadeIn(tween(750))

private val popExitTransition = slideOutHorizontally(
    animationSpec = tween(750)
) { it } + fadeOut(tween(750))

fun NavGraphBuilder.onboardingGraph(navController: NavController) {
    navigation<OnboardingGraph>(startDestination = Welcome) {

        composable<Welcome>(
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition },
        ) {
            WelcomeScreen(
                onGetStarted = {
                    navController.navigate(ChooseAccountType)
                })
        }

        composable<ChooseAccountType>(
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition },
        ) { backStackEntry ->

            // Graph-scoped ViewModel
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<OnboardingGraph>()
            }
            val viewModel: OnboardingViewModel = viewModel(parentEntry)

            ChooseAccountTypeScreen(
                selectedType = viewModel.uiState.collectAsState().value.selectedAccountType,
                onAccountTypeSelected = { type ->
                    viewModel.selectAccountType(type)
                    navController.navigate(PersonalDetails(accountType = type))
                })
        }

        composable<PersonalDetails>(
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition },
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<PersonalDetails>()

            PersonalDetailsScreen(
                accountType = args.accountType, onContinue = {
                    navController.navigate(IdVerification)
                })
        }

        composable<IdVerification>(
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition },
        ) {
            IdVerificationScreen(
                onVerified = {
                    navController.navigate(Success)
                })
        }

        composable<Success>(
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition },
        ) {

            // Consume back press intentionally
            BackHandler { /* onboarding complete — back is disabled */ }

            SuccessScreen(
                onEnterApp = {
                    navController.navigate(MainGraph) {
                        popUpTo(OnboardingGraph) { inclusive = true }
                    }
                })
        }
    }
}