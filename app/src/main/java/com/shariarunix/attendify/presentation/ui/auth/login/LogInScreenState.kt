package com.shariarunix.attendify.presentation.ui.auth.login

import com.google.firebase.auth.FirebaseUser
import com.shariarunix.attendify.domain.validator.PasswordValidationResult
import com.shariarunix.attendify.utils.Resource

data class LogInScreenState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: PasswordValidationResult? = null,
    val isPasswordVisible: Boolean = false,
    val isRememberUser: Boolean = false,
    val loginResult: Resource<FirebaseUser>? = null
)