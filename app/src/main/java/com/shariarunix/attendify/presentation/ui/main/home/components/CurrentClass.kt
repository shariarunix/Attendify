package com.shariarunix.attendify.presentation.ui.main.home.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.data.entity.classEntity.ClassEntity
import com.shariarunix.attendify.domain.model.ClassModel
import com.shariarunix.attendify.presentation.ui.theme.Blue40
import com.shariarunix.attendify.presentation.ui.theme.Blue80
import com.shariarunix.attendify.presentation.ui.theme.Gray40
import com.shariarunix.attendify.presentation.ui.theme.RoundedCornerShape10Dp
import com.shariarunix.attendify.presentation.ui.theme.White40
import com.shariarunix.attendify.presentation.ui.theme.White80
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily
import com.shariarunix.attendify.utils.CORNER_SIZE

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CurrentClassCardPreview() {
    CurrentClassCard(
        classModel = ClassModel(
            classID = "",
            classTitle = "Android Application Development",
            classCode = "CSE301",
            classDescription = "This class is about Android Application Development",
            classDate = "20/02/2003",
            classStartTime = "06:00 AM",
            classEndTime = "06:00 PM",
            classLocation = "Gouripur",
            classTeacherID = "",
            studentIDs = emptyList<String>()
        ),
        isUpcoming = true
    ) {}
}

@Composable
fun CurrentClassCard(
    modifier: Modifier = Modifier,
    classModel: ClassModel,
    isUpcoming: Boolean,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape10Dp
            )
            .clip(shape = RoundedCornerShape10Dp)
            .clickable {
                onClick(classModel.classID)
            }
            .padding(16.dp)
    ) {
        Text(
            text = if (isUpcoming) "Upcoming" else "Ongoing",
            fontSize = 16.sp,
            fontFamily = bodyFontFamily,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = classModel.classTitle,
            fontSize = 28.sp,
            fontFamily = displayFontFamily,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Medium
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(0.5.dp)
                .background(color = MaterialTheme.colorScheme.onPrimary)
        )
        Text(
            text = "At ${classModel.classLocation}",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
            fontFamily = bodyFontFamily,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = "Start Time",
                    fontFamily = bodyFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = classModel.classStartTime,
                    fontFamily = bodyFontFamily,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceContainer)
            )

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "End Time",
                    fontFamily = bodyFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = classModel.classEndTime,
                    fontSize = 22.sp,
                    fontFamily = bodyFontFamily,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}