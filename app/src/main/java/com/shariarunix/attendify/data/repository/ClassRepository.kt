package com.shariarunix.attendify.data.repository

import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.entity.classEntity.ClassEntity
import com.shariarunix.attendify.domain.model.ClassModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClassRepository @Inject constructor(
    private val sPref: SharedPref
) {

    companion object {
        const val CLASS_COLLECTION = "classes"
    }

    private var _todayClasses = MutableStateFlow(emptyList<ClassModel>())
    val todayClasses = _todayClasses.asStateFlow()
}