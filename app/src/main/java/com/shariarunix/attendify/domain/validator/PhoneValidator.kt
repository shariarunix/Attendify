package com.shariarunix.attendify.domain.validator

import com.shariarunix.attendify.utils.PHONE_BLANK
import com.shariarunix.attendify.utils.PHONE_VALID

typealias PhoneNumber = String

class PhoneValidator {
    fun execute(phoneNumber: PhoneNumber): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = PHONE_BLANK
            )
        }

        if (!phoneNumber.isValidate()) {
            return ValidationResult(
                successful = false,
                errorMessage = PHONE_VALID
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}

fun PhoneNumber.isValidate(): Boolean {
    return this.matches(
        regex = Regex("(^([+]{1}[8]{2}|0088)?(01){1}[3-9]{1}\\d{8})\$")
    )
}
