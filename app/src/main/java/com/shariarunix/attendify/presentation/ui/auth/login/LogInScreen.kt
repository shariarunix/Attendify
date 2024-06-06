package com.shariarunix.attendify.presentation.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.presentation.commons.CheckBoxWithLabel
import com.shariarunix.attendify.presentation.commons.EmailInputField
import com.shariarunix.attendify.presentation.ui.auth.commons.AuthFooter
import com.shariarunix.attendify.presentation.ui.auth.commons.AuthHeader
import com.shariarunix.attendify.utils.EMAIL_HINT
import com.shariarunix.attendify.presentation.commons.ErrorText
import com.shariarunix.attendify.presentation.commons.PasswordError
import com.shariarunix.attendify.presentation.commons.PasswordInputField
import com.shariarunix.attendify.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    val uiState = MutableStateFlow(LogInScreenState())
    LogInScreen(
        uiState = uiState.collectAsState(),
        onEvent = {},
        gotoForgotPasswordScreen = {},
        gotoSignUpScreen = {},
        logInComplete = {}
    )
}

@Composable
fun LogInScreen(
    uiState: State<LogInScreenState>,
    onEvent: (LogInScreenEvent) -> Unit,
    gotoForgotPasswordScreen: () -> Unit,
    gotoSignUpScreen: () -> Unit,
    logInComplete: () -> Unit
) {
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
                    title = "Login",
                    subTitle = "To Your Account"
                ) // Header Section

                // Email
                EmailInputField(
                    value = uiState.value.email,
                    labelText = "Email Address",
                    hintText = EMAIL_HINT,
                    isError = uiState.value.emailError != null,
                    imeAction = ImeAction.Next
                ) {
                    onEvent(LogInScreenEvent.OnEmailChanged(it))
                }

                ErrorText(text = uiState.value.emailError)

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                PasswordInputField(
                    value = uiState.value.password,
                    labelText = "Password",
                    hintText = "Your Password",
                    isError = !(uiState.value.passwordError?.successful ?: true),
                    imeAction = ImeAction.Done,
                    showPass = uiState.value.isPasswordVisible,
                    showPassChange = { onEvent(LogInScreenEvent.IsPasswordVisible(it)) }
                ) {
                    onEvent(LogInScreenEvent.OnPasswordChanged(it))
                }

                PasswordError(passwordErrorResult = uiState.value.passwordError)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CheckBoxWithLabel(
                        checkedState = uiState.value.isRememberUser,
                        labelText = "Remember me"
                    ) {
                        onEvent(LogInScreenEvent.IsRememberUser(it))
                    }

                    Text(
                        text = "Forgot password",
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(4.dp))
                            .clickable { gotoForgotPasswordScreen() }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                } // Remember User and Forgot Password

                Spacer(modifier = Modifier.height(40.dp))

                AuthFooter(
                    btnText = "Login",
                    bottomText = "Don't have an account?",
                    bottomTextBtn = "Create One",
                    gotoAnotherScreen = gotoSignUpScreen,
                    submitBtnOnClick = {
                        onEvent(LogInScreenEvent.OnLogin)
                    }
                ) // Footer Section

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
                } // Progress Bar

                LaunchedEffect(key1 = uiState.value.loginResult) {
                    /* Login Functionality */
                    uiState.value.loginResult?.let {
                        when (it) {
                            is Resource.Failure -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Something went wrong. Please try again.")
                                }
                                showLoading = false
                            }

                            is Resource.Loading -> {
                                showLoading = true
                            }

                            is Resource.Success -> {
                                logInComplete()
                                showLoading = false
                            }
                        }
                    }
                } // Login LaunchEffect
            } // Column
        } // Surface
    } // Scaffold
}