package com.shariarunix.attendify.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.shariarunix.attendify.domain.viewModels.AddClassViewModel
import com.shariarunix.attendify.presentation.ui.auth.commons.AuthScreens
import com.shariarunix.attendify.presentation.ui.main.commons.MainScreens
import com.shariarunix.attendify.presentation.ui.addClassScreen.AddClassScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    appNavController: NavHostController,
    isLoggedIn: Boolean,
    onBackPressed: () -> Unit
) {
    NavHost(
        navController = appNavController,
        startDestination = if (isLoggedIn) Screen.MainScreens.route else Screen.AuthScreens.route,
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
        composable(route = Screen.AuthScreens.route) {
            AuthScreens(
                modifier = modifier,
                appNavController = appNavController,
                onBackPressed = onBackPressed
            )
        }
        composable(route = Screen.MainScreens.route) {
            MainScreens(
                modifier = modifier,
                appNavController = appNavController
            )
        }
        composable(route = Screen.AddClassScreen.route) {
            val viewModel = hiltViewModel<AddClassViewModel>()
            AddClassScreen(
                modifier = modifier,
                uiState = viewModel.uiState.collectAsState(),
                onEvent = viewModel::onEvent,
                onBackPressed = onBackPressed
            )
        }
    }
}

