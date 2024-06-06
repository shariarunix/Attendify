package com.shariarunix.attendify.domain.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.repository.ClassRepository
import com.shariarunix.attendify.data.repository.UserRepository
import com.shariarunix.attendify.presentation.ui.main.home.HomeScreenEvent
import com.shariarunix.attendify.presentation.ui.main.home.HomeScreenState
import com.shariarunix.attendify.utils.getTimeInFormat
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
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val classRepo: ClassRepository,
    private val sPref: SharedPref
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        updateSystemTime()

        if (!sPref.isDataLoaded()) {
            /*
                * If user data is not fetched from server by MainViewModel, then it fetch from here.
                * If User just logged in or signup then it will fetch from here.
             */
            viewModelScope.launch(Dispatchers.IO) {
                userRepo.fetchUserData(sPref.getUserID()!!)
            }
            if (sPref.isRememberUser()) {
                sPref.dataLoaded(true)
            }
        }

        userRepo.userData.combine(classRepo.todayClasses) { userData, todayClasses ->
            _uiState.update {
                it.copy(
                    userName = userData.userName,
                    userProfileImage = userData.userProfileImage,
                    isTeacher = userData.teacher,
                    todayClasses = todayClasses
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeScreenEvent) {
        TODO("Not yet implemented")
    }

    private fun updateSystemTime() {
        val currentTime = getTimeInFormat(
            millis = System.currentTimeMillis(),
            format = "hh:mm:a"
        )

        currentTime.split(":").let {
            var hours = it[0].toInt()
            val minutes = it[1].toInt()
            val amPm = it[2].uppercase()

            if (amPm == "PM" && hours != 12) {
                hours += 12
            } else if (amPm == "AM" && hours == 12) {
                hours = 0
            }

            _uiState.update { uiState ->
                uiState.copy(
                    greetingText = when (hours) {
                        in 7..11 -> "Good Morning"
                        in 12..17 -> "Good Afternoon"
                        in 18..20 -> "Good Evening"
                        else -> "Good Night"
                    }
                )
            }
        }
    }
}
