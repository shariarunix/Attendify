package com.shariarunix.attendify.presentation.ui.auth.signup

import com.google.firebase.auth.FirebaseUser
import com.shariarunix.attendify.domain.validator.PasswordValidationResult
import com.shariarunix.attendify.utils.Resource

data class SignupScreenState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: PasswordValidationResult? = null,
    val isPasswordVisible: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isConfirmPasswordVisible: Boolean = false,
    val signUpResult: Resource<FirebaseUser>? = null
)
