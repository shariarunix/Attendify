package com.shariarunix.attendify.presentation.ui.addClassScreen

sealed class AddClassScreenEvent {
    data class OnClassTitleChange(val title: String) : AddClassScreenEvent()
    data class OnClassCodeChange(val code: String) : AddClassScreenEvent()
    data class OnClassLocationChange(val location: String) : AddClassScreenEvent()
    data class OnClassDescriptionChange(val description: String) : AddClassScreenEvent()
    data class OnRepeatedClassOptionChange(val value: Int) : AddClassScreenEvent()

    data class OnSelectedCustomRepeatOptionChange(
        val index: Int,
        val isEnable: Boolean
    ) : AddClassScreenEvent()

    data class IsRepeatedOptionDialogShow(val value: Boolean) : AddClassScreenEvent()
    data class IsTimePickerDialogShow(val value: Boolean) : AddClassScreenEvent()
    data object OnSaveClass : AddClassScreenEvent()
}