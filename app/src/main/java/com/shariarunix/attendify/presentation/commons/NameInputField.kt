package com.shariarunix.attendify.presentation.commons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily

@Composable
fun NameInputField(
    value: String,
    labelText: String,
    hintText: String,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
    onValueChange: (String) -> Unit
) {
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
            fontFamily = bodyFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        ),
        hintText = {
            Text(
                text = hintText,
                fontSize = 18.sp,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                fontWeight = FontWeight.Light
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "Name",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        isError = isError
    )
}