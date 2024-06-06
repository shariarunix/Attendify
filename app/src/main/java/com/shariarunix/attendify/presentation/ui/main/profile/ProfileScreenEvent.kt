package com.shariarunix.attendify.presentation.ui.main.profile

sealed class ProfileScreenEvent {
    data class OnDynamicThemeChange(val isDynamicTheme: Boolean) : ProfileScreenEvent()
    data class OnDarkThemeChange(val isDarkTheme: Boolean) : ProfileScreenEvent()

    data class IsUserInfoChangeDialogShow(val isShowDialog: Boolean) : ProfileScreenEvent()

    data class OnNameChange(val name: String) : ProfileScreenEvent()
    data class OnPhoneNumberChange(val phoneNumber: String) : ProfileScreenEvent()
    data class IsUserTeacher(val isTeacher: Boolean) : ProfileScreenEvent()

    data object OnChangeUserInfo : ProfileScreenEvent()
    data object OnAccountDelete : ProfileScreenEvent()
    data object OnLogOut : ProfileScreenEvent()
}