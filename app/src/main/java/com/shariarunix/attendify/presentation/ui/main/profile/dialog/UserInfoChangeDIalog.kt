package com.shariarunix.attendify.presentation.ui.main.profile.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.commons.ButtonWithProgress
import com.shariarunix.attendify.presentation.commons.ErrorText
import com.shariarunix.attendify.presentation.commons.NameInputField
import com.shariarunix.attendify.presentation.commons.PhoneInputField
import com.shariarunix.attendify.presentation.commons.SwitchWithCustomColors
import com.shariarunix.attendify.presentation.commons.TopAppBar
import com.shariarunix.attendify.presentation.ui.main.profile.ProfileScreenEvent
import com.shariarunix.attendify.presentation.ui.main.profile.ProfileScreenState
import com.shariarunix.attendify.presentation.ui.theme.AttendifyTheme
import com.shariarunix.attendify.presentation.ui.theme.RoundedCornerShape10Dp
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily
import com.shariarunix.attendify.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserInfoChangeDialogPrev() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoChangeDialog(
    modifier: Modifier = Modifier,
    uiState: ProfileScreenState,
    onEvent: (ProfileScreenEvent) -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    if (uiState.isUserInfoChangeDialogShow) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
            dragHandle = {
                TopAppBar(
                    title = "Change User Info",
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

            var isNameChanged by rememberSaveable {
                mutableStateOf(false)
            }

            var isPhoneChanged by rememberSaveable {
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

                            NameInputField(
                                value = if (isNameChanged) {
                                    uiState.name
                                } else uiState.userData?.userName ?: "Loading",
                                labelText = "Full Name",
                                hintText = uiState.userData?.userName ?: "Loading",
                                isError = uiState.nameError != null,
                                imeAction = ImeAction.Next,
                            ) {
                                isNameChanged = true
                                onEvent(ProfileScreenEvent.OnNameChange(it))
                            }

                            ErrorText(text = uiState.nameError)

                            Spacer(modifier = Modifier.height(16.dp))

                            PhoneInputField(
                                value = if (isPhoneChanged) {
                                    uiState.phone
                                } else uiState.userData?.userPhone ?: "Loading",
                                labelText = "Phone Number",
                                hintText = uiState.userData?.userPhone ?: "Loading",
                                isError = uiState.phoneError != null,
                                imeAction = ImeAction.Done,
                            ) {
                                isPhoneChanged = true
                                onEvent(ProfileScreenEvent.OnPhoneNumberChange(it))
                            }

                            ErrorText(text = uiState.phoneError)

                            Spacer(modifier = Modifier.height(20.dp))

                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceContainer,
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceContainer,
                                        shape = RoundedCornerShape10Dp
                                    )
                                    .clip(RoundedCornerShape10Dp)
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_teacher),
                                    contentDescription = "Teacher",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )

                                Text(
                                    text = "Are you teacher?",
                                    fontFamily = bodyFontFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = if (uiState.isUserTeacher) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .weight(1F)
                                )

                                SwitchWithCustomColors(
                                    checked = uiState.isUserTeacher,
                                    onCheckedChange = { onEvent(ProfileScreenEvent.IsUserTeacher(it)) }
                                )
                            } // Are You Teacher Row
                        } // Column

                        ButtonWithProgress(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp),
                            onClick = {
                                onEvent(ProfileScreenEvent.OnChangeUserInfo)
                            },
                            showProgress = showProgress,
                            buttonText = "Change"
                        ) // Button For Change User Info

                        Spacer(
                            modifier = Modifier
                                .height(32.dp)
                        )

                        LaunchedEffect(key1 = uiState.changeUserInfoResult) {
                            uiState.changeUserInfoResult?.let {
                                when (it) {
                                    is Resource.Failure -> {
                                        scope.launch {
                                            val snackbarResult = snackbarHostState.showSnackbar(
                                                message = "Something went wrong",
                                                actionLabel = "Try again",
                                                duration = SnackbarDuration.Short
                                            )
                                            when (snackbarResult) {
                                                SnackbarResult.ActionPerformed -> {
                                                    onEvent(ProfileScreenEvent.OnChangeUserInfo)
                                                }

                                                SnackbarResult.Dismissed -> {
                                                    showProgress = false
                                                }
                                            }
                                        }
                                    }

                                    Resource.Loading -> {
                                        showProgress = true
                                    }

                                    is Resource.Success -> {
                                        sheetState.hide()
                                        onDismissRequest()
                                        showProgress = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}