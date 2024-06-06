package com.shariarunix.attendify.presentation.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily
import com.shariarunix.attendify.utils.singleClick

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    fontWeight: FontWeight = FontWeight.Normal,
    showIcon: Boolean = true,
    buttonIcon: @Composable () -> Unit = {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back button",
            modifier = Modifier.fillMaxSize(),
            tint = MaterialTheme.colorScheme.onSurface
        )
    },
    onButtonClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showIcon) {
                Image(
                    painter = painterResource(id = R.drawable.attendify_logo),
                    contentDescription = stringResource(id = R.string.app_name),
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(38.dp),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Text(
                modifier = modifier.weight(1F),
                text = title,
                textAlign = TextAlign.Start,
                fontFamily = displayFontFamily,
                fontSize = 28.sp,
                fontWeight = fontWeight,
                color = titleColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = singleClick { onButtonClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .size(height = 40.dp, width = 40.dp)
                    .padding(0.dp)
            ) {
                buttonIcon()
            }
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceContainer)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TopAppBarPreview() {
    TopAppBar {

    }
}