package com.shariarunix.attendify.presentation.commons

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SwitchWithCustomColors(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colorScheme.primary,
        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        uncheckedBorderColor = Color.Unspecified
    )
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = colors
    )
}