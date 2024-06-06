package com.shariarunix.attendify.presentation.ui.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.ui.theme.AttendifyTheme
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily

@Preview(showBackground = true)
@Composable
private fun WelcomeBoxPreview() {
    AttendifyTheme {
        WelcomeBox(
            greetingText = "Good Morning",
            greetingIcon = Icons.Default.LightMode,
            userName = "Shariar Nafiz",
            userProfile = null
        )
    }
}

@Composable
fun WelcomeBox(
    modifier: Modifier = Modifier,
    greetingText: String,
    greetingIcon: ImageVector,
    userName: String,
    userProfile: String?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .height(56.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = greetingIcon,
                    contentDescription = "Greeting Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = greetingText,
                    fontFamily = bodyFontFamily,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = userName,
                fontSize = 28.sp,
                fontFamily = displayFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
        AsyncImage(
            model = userProfile,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(56.dp)
                .clip(shape = CircleShape),
            placeholder = painterResource(id = R.drawable.profile_placeholder),
            error = painterResource(id = R.drawable.profile_placeholder),
            contentScale = ContentScale.Crop
        )
    }
}