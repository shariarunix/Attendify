package com.shariarunix.attendify.domain.validator

import android.util.Patterns
import com.shariarunix.attendify.utils.EMAIL_BLANK
import com.shariarunix.attendify.utils.EMAIL_VALID

class EmailValidator {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = EMAIL_BLANK
            )
        }
        if (!isEmailValid(email)) {
            return ValidationResult(
                successful = false,
                errorMessage = EMAIL_VALID
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}