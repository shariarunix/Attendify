package com.shariarunix.attendify.presentation.ui.main.allClass.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.domain.model.ClassModel
import com.shariarunix.attendify.presentation.ui.theme.RoundedCornerShape10Dp
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily
import com.shariarunix.attendify.utils.LOADING_CLASS_DATA

@Preview
@Composable
private fun ClassDetailPreview() {
    ClassDetail(classModel = LOADING_CLASS_DATA, isUpcoming = true)
}

@Composable
fun ClassDetail(
    modifier: Modifier = Modifier,
    classModel: ClassModel,
    isUpcoming: Boolean
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_book),
            contentDescription = "Book Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = classModel.classTitle,
            fontSize = 32.sp,
            fontFamily = displayFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            lineHeight = 32.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = classModel.classCode,
                fontFamily = bodyFontFamily,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable {
                        /* TODO Show All Class of This Code Dialog */
                    }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
            Text(
                text = "At ${classModel.classLocation}",
                fontSize = 18.sp,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontStyle = FontStyle.Italic
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
        )
        Text(
            text = classModel.classDescription,
            fontSize = 16.sp,
            fontFamily = bodyFontFamily,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape10Dp
                )
                .padding(16.dp),
        ) {
            Text(
                text = if (isUpcoming) classModel.classDate else "Today",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                fontFamily = displayFontFamily,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .height(0.8.dp)
                    .background(color = MaterialTheme.colorScheme.onPrimary)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier) {
                    Text(
                        text = "Start Time",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = classModel.classStartTime,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(0.8.dp)
                        .background(color = MaterialTheme.colorScheme.onPrimary)
                )
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "End Time",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = classModel.classEndTime,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}