package com.shariarunix.attendify.presentation.ui.auth.login

sealed class LogInScreenEvent {
    data class OnEmailChanged(val email: String) : LogInScreenEvent()
    data class OnPasswordChanged(val password: String) : LogInScreenEvent()
    data class IsPasswordVisible(val isVisible: Boolean) : LogInScreenEvent()
    data class IsRememberUser(val remember: Boolean) : LogInScreenEvent()
    data object OnLogin : LogInScreenEvent()
}