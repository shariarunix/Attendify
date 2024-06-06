package com.shariarunix.attendify.presentation.ui.auth.signup

sealed class SignupScreenEvent {
    data class OnNameChanged(val value: String) : SignupScreenEvent()
    data class OnEmailChanged(val value: String) : SignupScreenEvent()
    data class OnPasswordChanged(val value: String) : SignupScreenEvent()
    data class IsPasswordVisible(val isVisible: Boolean) : SignupScreenEvent()
    data class OnConfirmPasswordChanged(val value: String) : SignupScreenEvent()
    data class IsConfirmPasswordVisible(val isVisible: Boolean) : SignupScreenEvent()
    data object OnSignUp : SignupScreenEvent()
}