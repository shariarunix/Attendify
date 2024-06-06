package com.shariarunix.attendify.domain.validator

import com.shariarunix.attendify.utils.NAME_BLANK
import com.shariarunix.attendify.utils.NAME_SPECIAL

typealias Name = String

class NameValidator {

    fun execute(name: Name): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = NAME_BLANK
            )
        }

        if (!name.hasSpecialChar()) {
            return ValidationResult(
                successful = false,
                errorMessage = NAME_SPECIAL
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}

fun Name.hasSpecialChar(): Boolean {
    return this.matches(
        regex = Regex("^[a-zA-Z0-9_.:' ()-]*\$")
    )
}