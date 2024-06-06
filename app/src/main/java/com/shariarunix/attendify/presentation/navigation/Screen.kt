package com.shariarunix.attendify.presentation.navigation

sealed class Screen(val route : String) {

    data object AddClassScreen : Screen("AddClassScreen")

    data object AuthScreens : Screen("AuthScreen"){
        data object LoginScreen : Screen("LoginScreen")
        data object SignupScreen : Screen("SignupScreen")
    }

    data object MainScreens : Screen("MainScreen") {
        data object HomeScreen : Screen("HomeScreen")
        data object AllClassScreen : Screen("AllClassScreen")
        data object ProfileScreen : Screen("ProfileScreen")
    }

}