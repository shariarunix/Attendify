package com.shariarunix.attendify.domain.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shariarunix.attendify.data.SharedPref
import com.shariarunix.attendify.data.repository.ClassRepository
import com.shariarunix.attendify.presentation.ui.addClassScreen.AddClassScreenEvent
import com.shariarunix.attendify.presentation.ui.addClassScreen.AddClassScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddClassViewModel @Inject constructor(
    private val classRepo: ClassRepository,
    private val sharedPref: SharedPref
) : ViewModel() {

    private var _uiState = MutableStateFlow(AddClassScreenState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AddClassScreenEvent) {
        when (event) {
            is AddClassScreenEvent.OnClassTitleChange -> {
                _uiState.update {
                    it.copy(
                        classTitle = event.title.trim()
                    )
                }
                validateTitle(event.title)
            }

            is AddClassScreenEvent.OnClassCodeChange -> {
                _uiState.update {
                    it.copy(
                        classCode = event.code.trim()
                    )
                }
                validateCode(event.code)
            }

            is AddClassScreenEvent.OnClassLocationChange -> {
                _uiState.update {
                    it.copy(
                        classLocation = event.location.trim()
                    )
                }
                validateLocation(event.location)
            }

            is AddClassScreenEvent.OnClassDescriptionChange -> {
                _uiState.update {
                    it.copy(
                        classDescription = event.description.trim()
                    )
                }
            }

            is AddClassScreenEvent.IsRepeatedOptionDialogShow -> {
                _uiState.update {
                    it.copy(
                        isClassRepeatDialogShow = event.value
                    )
                }
            }

            is AddClassScreenEvent.OnRepeatedClassOptionChange -> {
                _uiState.update {
                    it.copy(
                        selectedRepeatedOption = event.value
                    )
                }
            }

            is AddClassScreenEvent.OnSelectedCustomRepeatOptionChange -> {
                val updatedList = _uiState.value.selectedCustomRepeatOptionList
                    .toMutableList()
                    .apply {
                        this[event.index] = event.isEnable
                    }
                _uiState.update {
                    it.copy(
                        selectedCustomRepeatOptionList = updatedList
                    )
                }
                Log.d("BONK", "onEvent: ${uiState.value.selectedCustomRepeatOptionList}")
            }

            is AddClassScreenEvent.IsTimePickerDialogShow -> {
                _uiState.update {
                    it.copy(
                        isTimePickerDialogShow = event.value
                    )
                }
            }

            is AddClassScreenEvent.OnSaveClass -> {
                if (
                    validateTitle(uiState.value.classTitle) &&
                    validateCode(uiState.value.classCode) &&
                    validateLocation(uiState.value.classLocation)
                ) {

                }
            }
        }
    }

    private fun validateLocation(place: String): Boolean {
        return if (place.isBlank()) {
            _uiState.update {
                it.copy(
                    classLocationError = "Class location cannot be blank"
                )
            }
            false
        } else {
            _uiState.update {
                it.copy(
                    classLocationError = null
                )
            }
            true
        }
    }

    private fun validateCode(code: String): Boolean {
        return if (code.isBlank()) {
            _uiState.update {
                it.copy(classCodeError = "Class code cannot be blank")
            }
            false
        } else {
            _uiState.update {
                it.copy(
                    classCodeError = null
                )
            }
            true
        }
    }

    private fun validateTitle(title: String): Boolean {
        return if (title.isBlank()) {
            _uiState.update {
                it.copy(classTitleError = "Class title cannot be blank")
            }
            false
        } else {
            _uiState.update {
                it.copy(
                    classTitleError = null
                )
            }
            true
        }
    }
}