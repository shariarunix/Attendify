package com.shariarunix.attendify.presentation.ui.main.home

import com.shariarunix.attendify.domain.model.ClassModel

data class HomeScreenState(
    val greetingText: String = "Good Morning",
    val userName: String = "Loading",
    val userProfileImage: String? = null,
    val currentClass: ClassModel? = null,
    val isTeacher: Boolean = false,
    val isCurrentClassUpcoming: Boolean = false,
    val todayClasses: List<ClassModel> = emptyList()
)
