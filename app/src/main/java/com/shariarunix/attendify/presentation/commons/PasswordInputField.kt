package com.shariarunix.attendify.presentation.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily

@Composable
fun PasswordInputField(
    value: String,
    labelText: String,
    hintText: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
    showPass: Boolean = false,
    showPassChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {

    val eyeIconColor = if (showPass) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
    }

    Text(
        text = labelText,
        fontSize = 18.sp,
        fontFamily = bodyFontFamily,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Normal
    )
    Spacer(modifier = Modifier.height(10.dp))
    TextInputField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp,
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight.Normal,
        ),
        hintText = {
            Text(
                text = hintText,
                fontFamily = bodyFontFamily,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                fontWeight = FontWeight.Light
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock),
                contentDescription = "Password",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_eye),
                contentDescription = if (showPass) "Hide password" else "Show password",
                tint = eyeIconColor,
                modifier = Modifier
                    .size(32.dp)
                    .clip(shape = CircleShape)
                    .clickable {
                        showPassChange(!showPass)
                    }
                    .padding(7.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = if (showPass) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        isError = isError
    )
}