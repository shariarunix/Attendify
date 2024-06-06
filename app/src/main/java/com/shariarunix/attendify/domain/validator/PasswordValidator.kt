package com.shariarunix.attendify.domain.validator

import com.shariarunix.attendify.utils.PASSWORD_MIN_LENGTH

class PasswordValidator {

    fun execute(password: String): PasswordValidationResult {
        val isBlank: Boolean = password.isBlank()
        val hasMinimumLength: Boolean = password.length >= PASSWORD_MIN_LENGTH
        val hasSpecialChar: Boolean = validateSpecialChar(password)
        val hasCapitalChar: Boolean = validateCapitalChar(password)
        val hasNumber: Boolean = validateNumber(password)

        val successful = listOf(
            !isBlank,
            hasMinimumLength,
            hasSpecialChar,
            hasCapitalChar,
            hasNumber
        ).all { it }

        return PasswordValidationResult(
            isBlank = isBlank,
            hasMinimumLength = hasMinimumLength,
            hasSpecialChar = hasSpecialChar,
            hasCapitalChar = hasCapitalChar,
            hasNumber = hasNumber,
            successful = successful
        )
    }

    private fun validateSpecialChar(password: String): Boolean {
        return password.matches(
            Regex(".*[*@!#%&()^~{}].*")
        )
    }

    private fun validateCapitalChar(password: String): Boolean {
        return password.matches(Regex(".*[A-Z].*"))
    }

    private fun validateNumber(password: String): Boolean {
        return password.matches(Regex(".*[0-9].*"))
    }

}