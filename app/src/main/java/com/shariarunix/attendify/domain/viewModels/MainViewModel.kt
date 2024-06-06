package com.shariarunix.attendify.domain.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userRepo: UserRepository,
    sharedPref: SharedPref
) : ViewModel() {

    val isDynamicTheme = sharedPref.isDynamicTheme
    val isDarkTheme = sharedPref.isDarkTheme

    var isLoading by mutableStateOf(true)
        private set

    val isLoggedIn by mutableStateOf(sharedPref.isRememberUser())

    init {
        isLoading = if (sharedPref.getUserID() != null && sharedPref.isRememberUser()) {
            viewModelScope.launch(Dispatchers.IO) {
                userRepo.fetchUserData(sharedPref.getUserID()!!)
            }
            sharedPref.dataLoaded(true)
            false // is loading
        } else {
            sharedPref.dataLoaded(false)
            false // is loading
        }
    }
}