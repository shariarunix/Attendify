package com.shariarunix.attendify.presentation.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.presentation.ui.theme.Red80

@Composable
fun ErrorText(text: String?) {
    AnimatedVisibility(visible = text != null) {
        Text(
            text = text ?: "Error",
            color = MaterialTheme.colorScheme.error,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 3.dp)
        )
    }
}