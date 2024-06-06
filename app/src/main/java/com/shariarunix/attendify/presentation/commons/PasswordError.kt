package com.shariarunix.attendify.presentation.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.domain.validator.PasswordValidationResult
import com.shariarunix.attendify.utils.PASSWORD_BLANK
import com.shariarunix.attendify.utils.PASSWORD_CAPITAL
import com.shariarunix.attendify.utils.PASSWORD_LENGTH
import com.shariarunix.attendify.utils.PASSWORD_NUMBER
import com.shariarunix.attendify.utils.PASSWORD_SPECIAL


@Composable
fun PasswordError(passwordErrorResult: PasswordValidationResult?) {
    if (passwordErrorResult != null) {
        Column {
            passwordErrorResult.apply {
                AnimatedVisibility(visible = isBlank) {
                    Text(
                        text = PASSWORD_BLANK,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 3.dp)
                    )
                }
                if (!isBlank) {
                    AnimatedVisibility(visible = !hasMinimumLength) {
                        Text(
                            text = PASSWORD_LENGTH,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 3.dp)
                        )
                    }
                    AnimatedVisibility(visible = !hasCapitalChar) {
                        Text(
                            text = PASSWORD_CAPITAL,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 3.dp)
                        )
                    }
                    AnimatedVisibility(visible = !hasSpecialChar) {
                        Text(
                            text = PASSWORD_SPECIAL,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 3.dp)
                        )
                    }
                    AnimatedVisibility(visible = !hasNumber) {
                        Text(
                            text = PASSWORD_NUMBER,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}