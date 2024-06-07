package com.shariarunix.attendify.domain.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.repository.AuthRepository
import com.shariarunix.attendify.data.repository.UserRepository
import com.shariarunix.attendify.domain.validator.NameValidator
import com.shariarunix.attendify.domain.validator.PasswordValidator
import com.shariarunix.attendify.domain.validator.PhoneValidator
import com.shariarunix.attendify.domain.validator.hasSpecialChar
import com.shariarunix.attendify.domain.validator.isValidate
import com.shariarunix.attendify.presentation.ui.main.profile.ProfileScreenEvent
import com.shariarunix.attendify.presentation.ui.main.profile.ProfileScreenState
import com.shariarunix.attendify.utils.CONFIRM_PASS_BLANK
import com.shariarunix.attendify.utils.CONFIRM_PASS_MATCH
import com.shariarunix.attendify.utils.CURRENT_PASS_BLANK
import com.shariarunix.attendify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPref: SharedPref,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val nameValidator = NameValidator()
    private val phoneValidator = PhoneValidator()
    private val passwordValidator = PasswordValidator()

    private var _uiState = MutableStateFlow(ProfileScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        userRepository.userData.combine(sharedPref.isDynamicTheme) { userData, isDynamicTheme ->
            _uiState.update {
                it.copy(
                    userData = userData,
                    isDynamicTheme = isDynamicTheme,
                    isDarkTheme = sharedPref.isDarkTheme.value,
                    isUserTeacher = userData.teacher
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnDynamicThemeChange -> {
                sharedPref.setDynamicTheme(event.isDynamicTheme)
                _uiState.update {
                    it.copy(
                        isDynamicTheme = event.isDynamicTheme
                    )
                }
            }

            is ProfileScreenEvent.OnDarkThemeChange -> {
                sharedPref.setDarkTheme(event.isDarkTheme)
                _uiState.update {
                    it.copy(
                        isDarkTheme = event.isDarkTheme
                    )
                }
            }

            is ProfileScreenEvent.IsUserInfoChangeDialogShow -> {
                _uiState.update {
                    it.copy(
                        isUserInfoChangeDialogShow = event.isShowDialog
                    )
                }
            }

            is ProfileScreenEvent.OnNameChange -> {
                _uiState.update {
                    it.copy(
                        name = event.name
                    )
                }
                validateName()
            }

            is ProfileScreenEvent.OnPhoneNumberChange -> {
                _uiState.update {
                    it.copy(
                        phone = event.phoneNumber
                    )
                }
                validatePhoneNumber()
            }

            is ProfileScreenEvent.IsUserTeacher -> {
                _uiState.update {
                    it.copy(
                        isUserTeacher = event.isTeacher
                    )
                }
            }

            is ProfileScreenEvent.OnChangeUserInfo -> {
                // Working Here
                val userName = if (uiState.value.name.isBlank() ||
                    !uiState.value.name.hasSpecialChar()
                ) {
                    _uiState.value.userData?.userName
                } else {
                    uiState.value.name
                }

                val phoneNumber = if (uiState.value.phone.isBlank() ||
                    !uiState.value.phone.isValidate()
                ) {
                    _uiState.value.userData?.userPhone
                } else {
                    uiState.value.phone
                }
                val isUserTeacher = uiState.value.isUserTeacher

                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update {
                        it.copy(
                            changeUserInfoResult = Resource.Loading
                        )
                    }
                    val changeResult = userRepository.changeUserData(
                        userName = userName ?: "Something went wrong",
                        userPhone = phoneNumber ?: "Something went wrong",
                        isTeacher = isUserTeacher
                    )
                    _uiState.update {
                        it.copy(
                            changeUserInfoResult = changeResult
                        )
                    }
                }
            }

            is ProfileScreenEvent.ClearChangeUserInfoResult -> {
                _uiState.update {
                    it.copy(
                        changeUserInfoResult = null
                    )
                }
            }

            is ProfileScreenEvent.IsPasswordChangeDialogShow -> {
                _uiState.update {
                    it.copy(
                        isPasswordChangeDialogShow = event.isShowDialog
                    )
                }
            }

            is ProfileScreenEvent.IsConfirmPasswordShow -> {
                _uiState.update {
                    it.copy(
                        isConfirmPasswordShow = event.isShow
                    )
                }
            }

            is ProfileScreenEvent.IsCurrentPasswordShow -> {
                _uiState.update {
                    it.copy(
                        isCurrentPasswordShow = event.isShow
                    )
                }
            }

            is ProfileScreenEvent.IsNewPasswordShow -> {
                _uiState.update {
                    it.copy(
                        isNewPasswordShow = event.isShow
                    )
                }
            }

            is ProfileScreenEvent.OnCurrentPasswordChange -> {
                _uiState.update {
                    it.copy(
                        currentPassword = event.currentPassword
                    )
                }
                validateCurrentPassword()
            }

            is ProfileScreenEvent.OnNewPasswordChange -> {
                _uiState.update {
                    it.copy(
                        newPassword = event.newPassword
                    )
                }
                validatePassword()
            }

            is ProfileScreenEvent.OnConfirmPasswordChange -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword
                    )
                }
                validateConfirmPassword()
            }

            is ProfileScreenEvent.OnPasswordChange -> {
                val userEmail = _uiState.value.userData?.userEmail ?: "Something went wrong"
                val currentPassword = uiState.value.currentPassword
                val newPassword = uiState.value.confirmPassword

                if (validateCurrentPassword() && validatePassword() && validateConfirmPassword()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        _uiState.update {
                            it.copy(
                                changePasswordResult = Resource.Loading
                            )
                        }
                        val changeResult = authRepository.changePassword(
                            userEmail = userEmail,
                            currentPassword = currentPassword,
                            newPassword = newPassword
                        )
                        if (changeResult is Resource.Success) {
                            _uiState.update {
                                it.copy(
                                    currentPassword = "",
                                    newPassword = "",
                                    confirmPassword = "",
                                    isCurrentPasswordShow = false,
                                    isNewPasswordShow = false,
                                    isConfirmPasswordShow = false
                                )
                            }
                        }
                        _uiState.update {
                            it.copy(
                                changePasswordResult = changeResult
                            )
                        }
                    }
                }
            }

            is ProfileScreenEvent.ClearChangePasswordResult -> {
                _uiState.update {
                    it.copy(
                        changePasswordResult = null
                    )
                }
            }

            is ProfileScreenEvent.IsAccountDeleteDialogShow -> {

            }

            is ProfileScreenEvent.OnAccountDelete -> {

            }

            is ProfileScreenEvent.OnLogOut -> {

            }
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneResult = phoneValidator.execute(uiState.value.phone)
        _uiState.update {
            it.copy(phoneError = phoneResult.errorMessage)
        }
        return phoneResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = nameValidator.execute(uiState.value.name)
        _uiState.update {
            it.copy(nameError = nameResult.errorMessage)
        }
        return nameResult.successful
    }

    private fun validateCurrentPassword(): Boolean {
        if (uiState.value.currentPassword.isBlank()) {
            _uiState.update {
                it.copy(currentPasswordError = CURRENT_PASS_BLANK)
            }
            return false
        }

        _uiState.update {
            it.copy(currentPasswordError = null)
        }

        return true
    }

    private fun validatePassword(): Boolean {
        val passwordResult = passwordValidator.execute(uiState.value.newPassword)
        _uiState.update {
            it.copy(newPasswordError = passwordResult)
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

        if (uiState.value.confirmPassword != uiState.value.newPassword) {
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