package com.shariarunix.attendify.presentation.ui.main.profile

import com.shariarunix.attendify.domain.model.UserModel
import com.shariarunix.attendify.utils.Resource

data class ProfileScreenState(
    val userData: UserModel? = null,
    val isDynamicTheme: Boolean = false,
    val isDarkTheme: Boolean = false,
    val isUserInfoChangeDialogShow: Boolean = false,
    val isPasswordChangeDialogShow: Boolean = false,
    val isDeleteAccountDialogShow: Boolean = false,
    val name: String = "",
    val nameError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val isUserTeacher: Boolean = false,
    val changeUserInfoResult: Resource<Unit>? = null
)
