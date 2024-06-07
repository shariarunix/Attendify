package com.shariarunix.attendify.presentation.ui.main.profile

sealed class ProfileScreenEvent {
    data class OnDynamicThemeChange(val isDynamicTheme: Boolean) : ProfileScreenEvent()
    data class OnDarkThemeChange(val isDarkTheme: Boolean) : ProfileScreenEvent()

    data class IsUserInfoChangeDialogShow(val isShowDialog: Boolean) : ProfileScreenEvent()
    data class IsPasswordChangeDialogShow(val isShowDialog: Boolean) : ProfileScreenEvent()
    data class IsAccountDeleteDialogShow(val isShowDialog: Boolean) : ProfileScreenEvent()

    data class OnNameChange(val name: String) : ProfileScreenEvent()
    data class OnPhoneNumberChange(val phoneNumber: String) : ProfileScreenEvent()
    data class IsUserTeacher(val isTeacher: Boolean) : ProfileScreenEvent()

    data class OnCurrentPasswordChange(val currentPassword: String) : ProfileScreenEvent()
    data class OnNewPasswordChange(val newPassword: String) : ProfileScreenEvent()
    data class OnConfirmPasswordChange(val confirmPassword: String) : ProfileScreenEvent()

    data class IsCurrentPasswordShow(val isShow: Boolean) : ProfileScreenEvent()
    data class IsNewPasswordShow(val isShow: Boolean) : ProfileScreenEvent()
    data class IsConfirmPasswordShow(val isShow: Boolean) : ProfileScreenEvent()

    data object ClearChangeUserInfoResult : ProfileScreenEvent()
    data object ClearChangePasswordResult : ProfileScreenEvent()

    data object OnChangeUserInfo : ProfileScreenEvent()
    data object OnPasswordChange : ProfileScreenEvent()
    data object OnAccountDelete : ProfileScreenEvent()

    data object OnLogOut : ProfileScreenEvent()
}