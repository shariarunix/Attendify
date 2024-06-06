package com.shariarunix.attendify.presentation.ui.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.domain.model.ClassModel
import com.shariarunix.attendify.presentation.ui.theme.Blue80
import com.shariarunix.attendify.presentation.ui.theme.Gray40
import com.shariarunix.attendify.presentation.ui.theme.White40
import com.shariarunix.attendify.presentation.ui.theme.White80
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily
import com.shariarunix.attendify.utils.CORNER_SIZE

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ClassCardPreview() {
    ClassCard(
        classModel = ClassModel(
            classID = "",
            classTitle = "Demo Class",
            classCode = "Class Code",
            classDescription = "Demo Class Note",
            classDate = "20/02/2003",
            classStartTime = "06:00 AM",
            classEndTime = "06:00 PM",
            classLocation = "Gouripur",
            classTeacherID = "",
            studentIDs = emptyList<String>()
        ),
        isToday = true
    ) {}
}

@Composable
fun ClassCard(
    modifier: Modifier = Modifier,
    classModel: ClassModel,
    isToday: Boolean = false,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(shape = RoundedCornerShape(CORNER_SIZE))
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(CORNER_SIZE)
            )
            .clickable {
                onClick(classModel.classID)
            }
            .padding(16.dp),
    ) {
        Text(
            text = classModel.classTitle,
            fontSize = 22.sp,
            fontFamily = displayFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "At ${classModel.classLocation}",
            fontSize = 16.sp,
            fontFamily = bodyFontFamily,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontStyle = FontStyle.Italic
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = classModel.classStartTime,
                fontSize = 16.sp,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
            Text(
                text = if (isToday) "Today" else classModel.classDate,
                fontSize = 16.sp,
                fontFamily = bodyFontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}