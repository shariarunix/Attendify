package com.shariarunix.attendify.presentation.ui.auth.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shariarunix.attendify.presentation.commons.EmailInputField
import com.shariarunix.attendify.presentation.ui.auth.commons.AuthFooter
import com.shariarunix.attendify.presentation.ui.auth.commons.AuthHeader
import com.shariarunix.attendify.presentation.commons.ErrorText
import com.shariarunix.attendify.presentation.commons.NameInputField
import com.shariarunix.attendify.presentation.commons.PasswordError
import com.shariarunix.attendify.presentation.commons.PasswordInputField
import com.shariarunix.attendify.utils.EMAIL_HINT
import com.shariarunix.attendify.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignupScreenPreview() {
    val uiState = MutableStateFlow(SignupScreenState())
    SignupScreen(
        uiState = uiState.collectAsState(),
        onEvent = { },
        gotoLoginScreen = { },
        signUpComplete = { }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupScreen(
    uiState: State<SignupScreenState>,
    onEvent: (SignupScreenEvent) -> Unit,
    gotoLoginScreen: () -> Unit,
    signUpComplete: () -> Unit
) {
    val bringIntoViewRequester = BringIntoViewRequester()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true
                    )
            ) {
                AuthHeader(
                    title = "Sign Up",
                    subTitle = "To Explore The Attendify"
                ) // Header Section

                // Name
                NameInputField(
                    value = uiState.value.name,
                    labelText = "Full Name",
                    hintText = "Your Name",
                    isError = uiState.value.nameError != null,
                    imeAction = ImeAction.Next
                ) {
                    onEvent(SignupScreenEvent.OnNameChanged(it))
                }

                ErrorText(text = uiState.value.nameError)

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                EmailInputField(
                    value = uiState.value.email,
                    labelText = "Email Address",
                    hintText = EMAIL_HINT,
                    isError = uiState.value.emailError != null,
                    imeAction = ImeAction.Next
                ) {
                    onEvent(SignupScreenEvent.OnEmailChanged(it))
                }

                ErrorText(text = uiState.value.emailError)

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                PasswordInputField(
                    value = uiState.value.password,
                    labelText = "Password",
                    hintText = "Your Password",
                    isError = !(uiState.value.passwordError?.successful ?: true),
                    imeAction = ImeAction.Next,
                    showPass = uiState.value.isPasswordVisible,
                    showPassChange = { onEvent(SignupScreenEvent.IsPasswordVisible(it)) }
                ) {
                    onEvent(SignupScreenEvent.OnPasswordChanged(it))
                }

                PasswordError(passwordErrorResult = uiState.value.passwordError)

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password
                PasswordInputField(
                    value = uiState.value.confirmPassword,
                    labelText = "Confirm Password",
                    hintText = "Confirm Your Password",
                    isError = uiState.value.confirmPasswordError != null,
                    imeAction = ImeAction.Done,
                    showPass = uiState.value.isConfirmPasswordVisible,
                    showPassChange = { onEvent(SignupScreenEvent.IsConfirmPasswordVisible(it)) },
                    modifier = Modifier.onFocusEvent {
                        if (it.hasFocus) {
                            scope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                ) {
                    onEvent(SignupScreenEvent.OnConfirmPasswordChanged(it))
                }

                ErrorText(text = uiState.value.confirmPasswordError)

                Spacer(
                    modifier = Modifier.height(40.dp)
                )

                AuthFooter(
                    btnText = "Sign Up",
                    bottomText = "Already have an account?",
                    bottomTextBtn = "Log in",
                    modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
                    gotoAnotherScreen = gotoLoginScreen,
                    submitBtnOnClick = {
                        onEvent(SignupScreenEvent.OnSignUp)
                    }
                )

                AnimatedVisibility(showLoading) {
                    Column {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                }

                LaunchedEffect(key1 = uiState.value.signUpResult) {
                    uiState.value.signUpResult?.let {
                        when (it) {
                            is Resource.Failure -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar(it.exception.message.toString())
                                }
                                showLoading = false
                            }

                            is Resource.Loading -> {
                                showLoading = true
                            }

                            is Resource.Success -> {
                                signUpComplete()
                                showLoading = false
                            }
                        }
                    }
                } // Signup Launched Effect
            } // Lazy Column
        } // Surface
    } // Scaffold
}