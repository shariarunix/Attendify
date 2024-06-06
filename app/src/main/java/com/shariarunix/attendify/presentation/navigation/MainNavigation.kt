package com.shariarunix.attendify.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shariarunix.attendify.domain.viewModels.AllClassViewModel
import com.shariarunix.attendify.presentation.ui.main.allClass.AllClassScreen
import com.shariarunix.attendify.presentation.ui.main.home.HomeScreen
import com.shariarunix.attendify.presentation.ui.main.profile.ProfileScreen
import com.shariarunix.attendify.domain.viewModels.HomeViewModel
import com.shariarunix.attendify.domain.viewModels.ProfileViewModel

@Composable
fun MainNavigation(
    appNavController: NavHostController,
    mainNavController: NavHostController,
) {
    NavHost(
        navController = mainNavController,
        startDestination = Screen.MainScreens.HomeScreen.route,
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
        composable(route = Screen.MainScreens.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                uiState = viewModel.uiState.collectAsState(),
                onEvent = viewModel::onEvent,
                gotoAddClassScreen = {
                    appNavController.navigate(route = Screen.AddClassScreen.route) {
                        launchSingleTop = true
                    }
                },
                gotoAllClassScreen = {
                    mainNavController.navigate(route = Screen.MainScreens.AllClassScreen.route) {
                        popUpTo(mainNavController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                })
        }
        composable(route = Screen.MainScreens.ProfileScreen.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                uiState = viewModel.uiState.collectAsState(),
                onEvent = viewModel::onEvent,
                onLogoutClick = {
                    // TODO Implement logout
                }
            )
        }

        composable(route = Screen.MainScreens.AllClassScreen.route) {
            val viewModel = hiltViewModel<AllClassViewModel>()
            AllClassScreen(
                uiState = viewModel.uiState.collectAsState(),
                onEvent = viewModel::onEvent
            )
        }
    }
}