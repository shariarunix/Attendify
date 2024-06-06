package com.shariarunix.attendify.presentation.ui.auth.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily

@Composable
fun AuthHeader(title: String, subTitle: String) {
    Column {
        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 56.sp,
            fontFamily = displayFontFamily,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = subTitle,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = displayFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}