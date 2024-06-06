package com.shariarunix.attendify.presentation.ui.addClassScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.commons.ErrorText
import com.shariarunix.attendify.presentation.commons.MessageInputField
import com.shariarunix.attendify.presentation.commons.TextInputField
import com.shariarunix.attendify.presentation.commons.TimePickerDialog
import com.shariarunix.attendify.presentation.commons.TopAppBar
import com.shariarunix.attendify.presentation.ui.addClassScreen.dialog.RepeatClassDialog
import com.shariarunix.attendify.utils.WEEK_DAYS
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddClassScreenPreview() {
    val uiState = MutableStateFlow(
        AddClassScreenState(
            classTitle = "Android App Development",
            classCode = "CSE265",
            classLocation = "Software Lab",
            classDescription = "Important Class",
            selectedRepeatedOption = 2,
            isTimePickerDialogShow = true
        )
    )
    AddClassScreen(
        uiState = uiState.collectAsState(),
        onEvent = {},
        onBackPressed = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClassScreen(
    modifier: Modifier = Modifier,
    uiState: State<AddClassScreenState>,
    onEvent: (AddClassScreenEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val state by remember {
        mutableStateOf(ScrollState(0))
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                showIcon = false,
                title = "Add Your Class",
                titleColor = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            ) {
                onBackPressed()
            }
        }) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = state)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TextInputField(
                    value = uiState.value.classTitle,
                    labelText = "Class Title",
                    hintText = "e.g. Android App Development",
                    isError = uiState.value.classTitleError != null,
                    imeAction = ImeAction.Next,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_book),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                ) {
                    onEvent(AddClassScreenEvent.OnClassTitleChange(it))
                } // Title Input

                ErrorText(text = uiState.value.classTitleError)
                Spacer(modifier = Modifier.height(16.dp))

                TextInputField(
                    value = uiState.value.classCode,
                    labelText = "Class Code",
                    hintText = "e.g. CSE265",
                    isError = uiState.value.classCodeError != null,
                    imeAction = ImeAction.Next,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_code),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                ) {
                    onEvent(AddClassScreenEvent.OnClassCodeChange(it))
                } // Code Input

                ErrorText(text = uiState.value.classCodeError)
                Spacer(modifier = Modifier.height(16.dp))

                TextInputField(
                    value = uiState.value.classLocation,
                    labelText = "Class Location",
                    hintText = "e.g. Software Lab",
                    isError = uiState.value.classLocationError != null,
                    imeAction = ImeAction.Next,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Place,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                ) {
                    onEvent(AddClassScreenEvent.OnClassLocationChange(it))
                } // Location Input

                ErrorText(text = uiState.value.classLocationError)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Class Note",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(6.dp))
                MessageInputField(
                    value = uiState.value.classDescription,
                    hintText = {
                        Text(
                            text = "e.g. Important Class (Optional)",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75F),
                            fontWeight = FontWeight.Light
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.None
                    )
                ) {
                    onEvent(AddClassScreenEvent.OnClassDescriptionChange(it))
                } // Message Input

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Repeat Class",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Row(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {
                                onEvent(AddClassScreenEvent.IsRepeatedOptionDialogShow(true))
                            }
                            .padding(start = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = uiState.value.repeatClassOptionList[uiState.value.selectedRepeatedOption],
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75F),
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = uiState.value.repeatClassOptionList[uiState.value.selectedRepeatedOption],
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75F),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } // Repeated Option

                Spacer(modifier = Modifier.height(10.dp))

                AnimatedContent(
                    targetState = uiState.value.selectedRepeatedOption,
                    label = "RepeatedOption",
                    transitionSpec = {
                        (slideInHorizontally(
                            animationSpec = tween(200),
                            initialOffsetX = { it }
                        ) + fadeIn(
                            animationSpec = tween(150, delayMillis = 75),
                            initialAlpha = 0f
                        )).togetherWith(
                            (slideOutHorizontally(
                                animationSpec = tween(200),
                                targetOffsetX = { -it }
                            ) + fadeOut(
                                animationSpec = tween(75),
                                targetAlpha = 1f
                            ))
                        )
                    }
                ) { targetState ->
                    when (targetState) {
                        /* If selectedOption = 0 means Class will be repeat once,
                        That's why DATE and TIME required */
                        0 -> {
                            Row {
                                Button(
                                    onClick = { /* TODO Date Dialog */ },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(60.dp)
                                ) {
                                    Text(
                                        text = "Date",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Button(
                                    onClick = { /* TODO Time Dialog */ },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(60.dp)
                                ) {
                                    Text(
                                        text = "Time",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                    )
                                }
                            }
                        }

                        /* If selectedOption = 1 means Class will be repeat daily,
                        That's why ONLY TIME required */
                        1 -> {
                            Button(
                                onClick = { /* TODO Time Dialog */ },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                            ) {
                                Text(
                                    text = "Time",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        }

                        /* If selectedOption = 2 means Class will be repeat custom,
                        That's why DATE and Time required for every day */
                        2 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(12.dp)
                            ) {
                                WEEK_DAYS.forEachIndexed { index, weekDay ->
                                    CustomDateTimeItem(
                                        weekDay = weekDay,
                                        index = index,
                                        startTime = null,
                                        endTime = null,
                                        isEnable = uiState.value.selectedCustomRepeatOptionList[index],
                                        onStartTimeClick = {
                                            /* TODO */
                                            onEvent(AddClassScreenEvent.IsTimePickerDialogShow(true))
                                        },
                                        onEndTimeClick = {
                                            /* TODO */
                                        }
                                    ) { isEnabled ->
                                        onEvent(
                                            AddClassScreenEvent.OnSelectedCustomRepeatOptionChange(
                                                index, isEnabled
                                            )
                                        )
                                    }
                                    if (index != WEEK_DAYS.lastIndex) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } // Surface END

        RepeatClassDialog(
            isShow = uiState.value.isClassRepeatDialogShow,
            repeatOptionList = uiState.value.repeatClassOptionList,
            onDismissRequest = {
                onEvent(AddClassScreenEvent.IsRepeatedOptionDialogShow(false))
            }
        ) {
            onEvent(AddClassScreenEvent.OnRepeatedClassOptionChange(it))
            onEvent(AddClassScreenEvent.IsRepeatedOptionDialogShow(false))
        }

        TimePickerDialog(
            state = rememberTimePickerState(
                initialHour = 11,
                initialMinute = 45,
                is24Hour = false
            ),
            showTimePickerDialog = uiState.value.isTimePickerDialogShow,
            onDismissRequest = {
                onEvent(AddClassScreenEvent.IsTimePickerDialogShow(false))
            }
        )
    }
}

