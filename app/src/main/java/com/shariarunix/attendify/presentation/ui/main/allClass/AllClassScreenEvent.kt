package com.shariarunix.attendify.presentation.ui.main.allClass

sealed class AllClassScreenEvent {

    data class OnSelectedDateChange(val date: Int) : AllClassScreenEvent()

}