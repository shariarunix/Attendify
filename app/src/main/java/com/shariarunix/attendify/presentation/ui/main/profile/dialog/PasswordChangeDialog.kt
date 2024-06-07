package com.shariarunix.attendify.presentation.ui.main.profile.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.shariarunix.attendify.presentation.commons.ButtonWithProgress
import com.shariarunix.attendify.presentation.commons.ErrorText
import com.shariarunix.attendify.presentation.commons.PasswordError
import com.shariarunix.attendify.presentation.commons.PasswordInputField
import com.shariarunix.attendify.presentation.commons.TopAppBar
import com.shariarunix.attendify.presentation.ui.main.profile.ProfileScreenEvent
import com.shariarunix.attendify.presentation.ui.main.profile.ProfileScreenState
import com.shariarunix.attendify.utils.Resource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordChangeDialog(
    modifier: Modifier = Modifier,
    uiState: ProfileScreenState,
    onEvent: (ProfileScreenEvent) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    if (uiState.isPasswordChangeDialogShow) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
            dragHandle = {
                TopAppBar(
                    title = "Change Password",
                    titleColor = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    showIcon = false
                ) {
                    scope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }
            },
            windowInsets = WindowInsets(
                bottom = WindowInsets
                    .statusBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
        ) {

            var showProgress by rememberSaveable {
                mutableStateOf(false)
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = modifier
                            .padding(start = 8.dp, end = 8.dp, bottom = 112.dp)
                    )
                },
                containerColor = MaterialTheme.colorScheme.background,
                modifier = modifier
                    .fillMaxSize(),
            ) { contentPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                                .padding(horizontal = 16.dp)
                                .verticalScroll(
                                    enabled = true,
                                    state = rememberScrollState()
                                )
                        ) {

                            Spacer(modifier = Modifier.height(16.dp))

                            PasswordInputField(
                                value = uiState.currentPassword,
                                labelText = "Current Password",
                                hintText = "Enter your current password",
                                imeAction = ImeAction.Next,
                                isError = uiState.currentPasswordError != null,
                                showPass = uiState.isCurrentPasswordShow,
                                showPassChange = {
                                    onEvent(ProfileScreenEvent.IsCurrentPasswordShow(it))
                                }
                            ) {
                                onEvent(ProfileScreenEvent.OnCurrentPasswordChange(it))
                            } // Current Password Field

                            ErrorText(text = uiState.currentPasswordError)

                            Spacer(modifier = Modifier.height(20.dp))

                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceContainer,
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            PasswordInputField(
                                value = uiState.newPassword,
                                labelText = "New Password",
                                hintText = "Enter your new password",
                                imeAction = ImeAction.Next,
                                isError = !(uiState.newPasswordError?.successful ?: true),
                                showPass = uiState.isNewPasswordShow,
                                showPassChange = {
                                    onEvent(ProfileScreenEvent.IsNewPasswordShow(it))
                                }
                            ) {
                                onEvent(ProfileScreenEvent.OnNewPasswordChange(it))
                            } // New Password Field

                            PasswordError(passwordErrorResult = uiState.newPasswordError)

                            Spacer(modifier = Modifier.height(16.dp))

                            PasswordInputField(
                                value = uiState.confirmPassword,
                                labelText = "Confirm Password",
                                hintText = "Confirm new password",
                                imeAction = ImeAction.Done,
                                isError = uiState.confirmPasswordError != null,
                                showPass = uiState.isConfirmPasswordShow,
                                showPassChange = {
                                    onEvent(ProfileScreenEvent.IsConfirmPasswordShow(it))
                                }
                            ) {
                                onEvent(ProfileScreenEvent.OnConfirmPasswordChange(it))
                            } // Confirm Password Field

                            ErrorText(text = uiState.confirmPasswordError)

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        ButtonWithProgress(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp),
                            onClick = {
                                onEvent(ProfileScreenEvent.OnPasswordChange)
                            },
                            showProgress = showProgress,
                            buttonText = "Change"
                        ) // Button For Change Password

                        Spacer(
                            modifier = Modifier
                                .height(32.dp)
                        )

                        LaunchedEffect(key1 = uiState.changePasswordResult) {
                            uiState.changePasswordResult?.let {
                                when (it) {
                                    is Resource.Success -> {
                                        sheetState.hide()
                                        onDismissRequest()
                                        onEvent(ProfileScreenEvent.ClearChangePasswordResult)
                                        showProgress = false
                                    }

                                    Resource.Loading -> {
                                        showProgress = true
                                    }

                                    is Resource.Failure -> {
                                        scope.launch {
                                            val snackbarResult = snackbarHostState.showSnackbar(
                                                message = it.exception.message
                                                    ?: "Something went wrong",
                                                actionLabel = "Try again",
                                                duration = SnackbarDuration.Short
                                            )
                                            when (snackbarResult) {
                                                SnackbarResult.ActionPerformed -> {
                                                    onEvent(ProfileScreenEvent.OnPasswordChange)
                                                }

                                                SnackbarResult.Dismissed -> {
                                                    showProgress = false
                                                }
                                            }
                                        }
                                        showProgress = false
                                    }
                                }
                            }
                        } // Launched Effect
                    } // Column Parent
                } // Surface
            } // Scaffold
        } // ModalBottomSheet
    }
}