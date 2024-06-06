package com.shariarunix.attendify.domain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shariarunix.attendify.data.repository.AuthRepository
import com.shariarunix.attendify.presentation.ui.auth.signup.SignupScreenEvent
import com.shariarunix.attendify.presentation.ui.auth.signup.SignupScreenState
import com.shariarunix.attendify.domain.validator.EmailValidator
import com.shariarunix.attendify.domain.validator.NameValidator
import com.shariarunix.attendify.domain.validator.PasswordValidator
import com.shariarunix.attendify.utils.CONFIRM_PASS_BLANK
import com.shariarunix.attendify.utils.CONFIRM_PASS_MATCH
import com.shariarunix.attendify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val validateNameUseCase = NameValidator()
    private val validateEmailUseCase = EmailValidator()
    private val validatePasswordUseCase = PasswordValidator()

    private var _uiState = MutableStateFlow(SignupScreenState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SignupScreenEvent) {
        when (event) {
            is SignupScreenEvent.OnNameChanged -> {
                _uiState.update {
                    it.copy(name = event.value)
                }
                validateName()
            }

            is SignupScreenEvent.OnEmailChanged -> {
                _uiState.update {
                    it.copy(email = event.value)
                }
                validateEmail()
            }

            is SignupScreenEvent.OnPasswordChanged -> {
                _uiState.update {
                    it.copy(password = event.value)
                }
                validatePassword()
            }

            is SignupScreenEvent.IsPasswordVisible -> {
                _uiState.update {
                    it.copy(isPasswordVisible = event.isVisible)
                }
            }

            is SignupScreenEvent.OnConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(confirmPassword = event.value)
                }
                validateConfirmPassword()
            }

            is SignupScreenEvent.IsConfirmPasswordVisible -> {
                _uiState.update {
                    it.copy(isConfirmPasswordVisible = event.isVisible)
                }
            }

            SignupScreenEvent.OnSignUp -> {
                if (validateName() && validateEmail() && validatePassword() && validateConfirmPassword()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        _uiState.update {
                            it.copy(signUpResult = Resource.Loading)
                        }
                        val result = authRepo.signup(
                            name = uiState.value.name,
                            email = uiState.value.email,
                            password = uiState.value.password
                        )
                        _uiState.update {
                            it.copy(signUpResult = result)
                        }
                    }
                }
            }
        }
    }


    private fun validateName(): Boolean {
        val nameResult = validateNameUseCase.execute(uiState.value.name)
        _uiState.update {
            it.copy(nameError = nameResult.errorMessage)
        }
        return nameResult.successful
    }

    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(uiState.value.email)
        _uiState.update {
            it.copy(emailError = emailResult.errorMessage)
        }
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(uiState.value.password)
        _uiState.update {
            it.copy(passwordError = passwordResult)
        }
        return passwordResult.successful
    }

    private fun validateConfirmPassword(): Boolean {
        if (uiState.value.confirmPassword.isBlank()) {
            _uiState.update {
                it.copy(confirmPasswordError = CONFIRM_PASS_BLANK)
            }
            return false
        }

        if (uiState.value.confirmPassword != uiState.value.password) {
            _uiState.update {
                it.copy(confirmPasswordError = CONFIRM_PASS_MATCH)
            }
            return false
        }

        _uiState.update {
            it.copy(confirmPasswordError = null)
        }
        return true
    }
}