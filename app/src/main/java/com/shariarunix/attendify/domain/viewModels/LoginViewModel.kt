package com.shariarunix.attendify.domain.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.repository.AuthRepository
import com.shariarunix.attendify.presentation.ui.auth.login.LogInScreenEvent
import com.shariarunix.attendify.domain.validator.EmailValidator
import com.shariarunix.attendify.presentation.ui.auth.login.LogInScreenState
import com.shariarunix.attendify.domain.validator.PasswordValidator
import com.shariarunix.attendify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val sPref: SharedPref
) : ViewModel() {

    private val validateEmailUseCase = EmailValidator()
    private val validatePasswordUseCase = PasswordValidator()

    private var _uiState = MutableStateFlow(LogInScreenState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LogInScreenEvent) {
        when (event) {
            is LogInScreenEvent.OnEmailChanged -> {
                _uiState.update {
                    it.copy(email = event.email)
                }
                validateEmail()
            }

            is LogInScreenEvent.OnPasswordChanged -> {
                _uiState.update {
                    it.copy(password = event.password)
                }
                validatePassword()
            }

            is LogInScreenEvent.IsPasswordVisible -> {
                _uiState.update {
                    it.copy(isPasswordVisible = event.isVisible)
                }
            }

            is LogInScreenEvent.IsRememberUser -> {
                _uiState.update {
                    it.copy(isRememberUser = event.remember)
                }
                sPref.rememberUser(event.remember) // Remember user for future
            }

            is LogInScreenEvent.OnLogin -> {
                if (validateEmail() && validatePassword()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        _uiState.update {
                            it.copy(loginResult = Resource.Loading)
                        }
                        val result = authRepo.login(uiState.value.email, uiState.value.password)
                        _uiState.update {
                            it.copy(loginResult = result)
                        }
                    }
                }
            }
        }
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(uiState.value.password)
        _uiState.update {
            it.copy(passwordError = passwordResult)
        }
        return passwordResult.successful
    }

    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(uiState.value.email)
        _uiState.update {
            it.copy(emailError = emailResult.errorMessage)
        }
        return emailResult.successful
    }

}