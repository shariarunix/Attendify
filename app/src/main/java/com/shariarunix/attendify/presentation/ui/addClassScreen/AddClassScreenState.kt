package com.shariarunix.attendify.presentation.ui.addClassScreen

import com.shariarunix.attendify.data.entity.classEntity.ClassDateTime

data class AddClassScreenState(
    val classTitle: String = "",
    val classTitleError: String? = null,
    val classCode: String = "",
    val classCodeError: String? = null,
    val classLocation: String = "",
    val classLocationError: String? = null,
    val classDescription: String = "",
    val selectedRepeatedOption: Int = 0,
    val repeatClassOptionList: List<String> = listOf("Once", "Daily", "Custom"), // Default values
    val selectedCustomRepeatOptionList: List<Boolean> = (0..6).map { false }, // Default values,
    val classDateTime: ClassDateTime? = null,
    val isClassRepeatDialogShow: Boolean = false,
    val isTimePickerDialogShow: Boolean = false
)
