package com.shariarunix.attendify.domain.viewModels

import androidx.lifecycle.ViewModel
import com.shariarunix.attendify.presentation.ui.main.allClass.AllClassScreenEvent
import com.shariarunix.attendify.presentation.ui.main.allClass.AllClassScreenState
import com.shariarunix.attendify.utils.getTimeInFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AllClassViewModel @Inject constructor() : ViewModel() {
    private var startDate: Int by Delegates.notNull()
    private var endDate: Int by Delegates.notNull()

    private var _uiState = MutableStateFlow(AllClassScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        val currentTime = getTimeInFormat(
            millis = System.currentTimeMillis(),
            format = "dd#hh#mm#a"
        )
        currentTime.split("#").let {
            val day = it[0].toInt()
            var hours = it[1].toInt()
            val minutes = it[2].toInt()
            val amPm = it[3].uppercase()

            if (amPm == "PM" && hours != 12) {
                hours += 12
            } else if (amPm == "AM" && hours == 12) {
                hours = 0
            }
            startDate = day
            endDate = if (startDate < 25) startDate + 7 else 31

            _uiState.update { uiState ->
                uiState.copy(
                    startDate = startDate,
                    endDate = endDate,
                )
            }
        }
    }

    fun onEvent(event: AllClassScreenEvent) {
        when (event) {
            is AllClassScreenEvent.OnSelectedDateChange -> {
                _uiState.update { uiState ->
                    uiState.copy(
                        selectedDateForClasses = event.date
                    )
                }
            }
        }
    }

}