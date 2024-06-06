package com.shariarunix.attendify.domain.validator

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String?
)
