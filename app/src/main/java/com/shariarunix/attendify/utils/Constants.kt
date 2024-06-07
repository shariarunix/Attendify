package com.shariarunix.attendify.utils

import androidx.compose.ui.unit.dp
import com.shariarunix.attendify.domain.model.ClassModel

val CORNER_SIZE = 10.dp

const val EMAIL_HINT = "Your@email.com"

// Email Error Text
const val EMAIL_BLANK = "Email cannot be blank"
const val EMAIL_VALID = "That's not a valid email"

const val PASSWORD_MIN_LENGTH = 8

// Password Error Text
const val PASSWORD_BLANK = "Password cannot be blank"
const val PASSWORD_LENGTH = "Password should contain at least $PASSWORD_MIN_LENGTH characters"
const val PASSWORD_CAPITAL = "At least one capital letter"
const val PASSWORD_SPECIAL = "At least one special character"
const val PASSWORD_NUMBER = "At least one number"

// Name Error Text
const val NAME_BLANK = "Name cannot be blank"
const val NAME_SPECIAL = "Please remove special characters"

// Confirm Password Error
const val CONFIRM_PASS_BLANK = "Confirm your password"
const val CONFIRM_PASS_MATCH = "Password you entered does not match"

// Phone Number Error Text
const val PHONE_BLANK = "Phone number cannot be blank"
const val PHONE_VALID = "That's not a valid phone number"

// Current Password Error Text
const val CURRENT_PASS_BLANK = "Current password cannot be blank"
const val CURRENT_PASS_MATCH = "Current password does not match"

// Week Days
val WEEK_DAYS = listOf("Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri")

val LOADING_CLASS_DATA = ClassModel(
    classID = "",
    classTitle = "Loading",
    classCode = "Loading",
    classDescription = "Loading",
    classDate = "Loading",
    classStartTime = "Loading",
    classEndTime = "Loading",
    classLocation = "Loading",
    classTeacherID = "Loading",
    studentIDs = emptyList()
)
