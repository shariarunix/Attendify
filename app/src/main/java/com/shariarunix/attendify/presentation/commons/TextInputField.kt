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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.presentation.ui.theme.RoundedCornerShape10Dp
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily

@Composable
fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hintText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.primary),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
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

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape10Dp
            ),
        textStyle = textStyle,
        cursorBrush = cursorBrush,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interaction,
        singleLine = true,
        enabled = enabled,
        readOnly = readOnly,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = borderWidth, color = borderColor, shape = RoundedCornerShape10Dp
                    )
                    .padding(start = 16.dp, end = if (trailingIcon == null) 16.dp else 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                    if (value.isEmpty()) {
                        hintText()
                    }
                }

                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    trailingIcon()
                }
            }
        }
    )
}

@Composable
fun TextInputField(
    value: String,
    labelText: String,
    hintText: String,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    Text(
        text = labelText,
        fontFamily = bodyFontFamily,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Normal
    )
    Spacer(modifier = Modifier.height(6.dp))
    TextInputField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = bodyFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        ),
        hintText = {
            Text(
                text = hintText,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75F),
                fontWeight = FontWeight.Light
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = imeAction
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        isError = isError
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TextInputPreview() {
    var value by remember {
        mutableStateOf("")
    }
    TextInputField(
        value = value,
        onValueChange = { value = it },
        hintText = {
            Text(
                text = "Enter Your Name",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(16.dp)
            ),
        isError = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Name",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.primary
            )
        },
    )

}