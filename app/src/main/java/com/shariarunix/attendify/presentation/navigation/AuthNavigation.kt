package com.shariarunix.attendify.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shariarunix.attendify.presentation.ui.auth.login.LogInScreen
import com.shariarunix.attendify.presentation.ui.auth.signup.SignupScreen
import com.shariarunix.attendify.domain.viewModels.LoginViewModel
import com.shariarunix.attendify.domain.viewModels.SignupViewModel

@Composable
fun AuthNavigation(appNavController: NavHostController) {
    val authNavController = rememberNavController()
    NavHost(
        navController = authNavController,
        startDestination = Screen.AuthScreens.LoginScreen.route,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(300),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        // Login Screen
        composable(route = Screen.AuthScreens.LoginScreen.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LogInScreen(
                uiState = viewModel.uiState.collectAsState(),
                onEvent = viewModel::onEvent,
                gotoForgotPasswordScreen = {

                },
                gotoSignUpScreen = {
                    authNavController.navigate(route = Screen.AuthScreens.SignupScreen.route) {
                        launchSingleTop = true
                    }
                },
                logInComplete = {
                    appNavController.navigate(route = Screen.MainScreens.route) {
                        popUpTo(Screen.AuthScreens.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Signup Screen
        composable(route = Screen.AuthScreens.SignupScreen.route) {
            val viewModel = hiltViewModel<SignupViewModel>()
            SignupScreen(
                uiState = viewModel.uiState.collectAsState(),
                onEvent = viewModel::onEvent,
                gotoLoginScreen = {
                    authNavController.popBackStack()
                },
                signUpComplete = {
                    appNavController.navigate(route = Screen.MainScreens.route) {
                        popUpTo(Screen.AuthScreens.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}