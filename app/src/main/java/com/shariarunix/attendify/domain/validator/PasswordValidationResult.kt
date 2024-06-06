package com.shariarunix.attendify.domain.validator

data class PasswordValidationResult(
    val isBlank: Boolean,
    val hasMinimumLength: Boolean,
    val hasSpecialChar: Boolean,
    val hasCapitalChar: Boolean,
    val hasNumber: Boolean,
    val successful: Boolean
)