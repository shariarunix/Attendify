package com.shariarunix.attendify.presentation.ui.addClassScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.presentation.ui.theme.Blue40
import com.shariarunix.attendify.presentation.ui.theme.Blue80
import com.shariarunix.attendify.presentation.ui.theme.Gray40
import com.shariarunix.attendify.presentation.ui.theme.Gray80
import com.shariarunix.attendify.presentation.ui.theme.White40
import com.shariarunix.attendify.presentation.ui.theme.White80

@Preview
@Composable
private fun CustomDateTimeItemPreview() {
    CustomDateTimeItem(
        weekDay = "Monday",
        index = 0,
        startTime = "10:00 AM",
        endTime = "11:00 AM",
        isEnable = true,
        onStartTimeClick = {},
        onEndTimeClick = {}
    ) {

    }
}

@Composable
fun CustomDateTimeItem(
    modifier: Modifier = Modifier,
    weekDay: String,
    index: Int,
    startTime: String?,
    endTime: String?,
    isEnable: Boolean = true,
    onStartTimeClick: (Int) -> Unit,
    onEndTimeClick: (Int) -> Unit,
    onCrossBtnClick: (Boolean) -> Unit
) {

    val rotationAngle by animateFloatAsState(
        targetValue = if (isEnable) 0f else 135f,
        label = "Icon Rotation Angle"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = White80,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .background(color = if (isEnable) White40 else Blue80, shape = CircleShape)
                .clip(CircleShape)
                .rotate(rotationAngle)
                .clickable {
                    onCrossBtnClick(!isEnable)
                }
                .padding(4.dp),
            imageVector = Icons.Rounded.Clear,
            contentDescription = "Date Select or Deselect Icon",
            tint = if (isEnable) Gray40 else White80
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            text = weekDay,
            fontSize = 18.sp,
            fontWeight = if (isEnable) FontWeight.Normal else FontWeight.Light,
            color = if (isEnable) Blue80 else Gray40
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .align(
                        Alignment.CenterVertically
                    )
                    .background(
                        color = if (isEnable) Blue40 else White40,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        enabled = isEnable
                    ) {
                        onStartTimeClick(index)
                    }
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (startTime.isNullOrEmpty()) "Start Time" else startTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = if (isEnable) Blue80 else Gray40,
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .align(
                        Alignment.CenterVertically
                    )
                    .background(
                        color = if (isEnable) Blue40 else White40,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        enabled = isEnable
                    ) {
                        onEndTimeClick(index)
                    }
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (endTime.isNullOrEmpty()) "End Time" else endTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = if (isEnable) Blue80 else Gray40,
                )
            }
        }
    }
}