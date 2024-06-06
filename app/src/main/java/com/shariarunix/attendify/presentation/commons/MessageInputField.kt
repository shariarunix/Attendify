package com.shariarunix.attendify.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.ui.theme.RoundedCornerShape10Dp


@Composable
fun MessageInputField(
    modifier: Modifier = Modifier,
    value: String,
    hintText: @Composable () -> Unit,
    textStyle: TextStyle = TextStyle(
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Normal
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    val interaction = remember { MutableInteractionSource() }
    val isFocused by interaction.collectIsFocusedAsState()

    var borderColor by remember { mutableStateOf(Color.Unspecified) }

    if (isFocused && !isError) borderColor = MaterialTheme.colorScheme.primary
    if (isError) borderColor = MaterialTheme.colorScheme.error
    if (!isFocused && !isError) borderColor = Color.Unspecified

    val backgroundColor = if (isError) {
        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15F)
    } else {
        MaterialTheme.colorScheme.surfaceContainer
    }

    val borderWidth = if (isFocused || isError) 1.25.dp else Dp.Unspecified

    if (isFocused && !isError) borderColor = MaterialTheme.colorScheme.primary
    if (isError) borderColor = MaterialTheme.colorScheme.error
    if (!isFocused && !isError) borderColor = Color.Unspecified

    BasicTextField(value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth, minHeight = TextFieldDefaults.MinHeight
            )
            .background(
                color = backgroundColor, shape = RoundedCornerShape10Dp
            ),
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interaction,
        singleLine = false,
        maxLines = 5,
        minLines = 2,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = RoundedCornerShape10Dp
                    )
                    .padding(16.dp), verticalAlignment = Alignment.Top
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_message),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F),
                    contentAlignment = Alignment.TopStart
                ) {
                    innerTextField()
                    if (value.isEmpty()) {
                        hintText()
                    }
                }
            }
        })
}