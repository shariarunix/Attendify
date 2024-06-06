package com.shariarunix.attendify.presentation.ui.main.allClass

import com.shariarunix.attendify.domain.model.ClassModel

data class AllClassScreenState(
    val isUpcoming: Boolean = true,
    val currentClass: ClassModel? = null,
    val startDate: Int? = null,
    val endDate: Int? = null,
    val selectedDateForClasses: Int? = null,
    val classesByDate: List<ClassModel> = emptyList()
)