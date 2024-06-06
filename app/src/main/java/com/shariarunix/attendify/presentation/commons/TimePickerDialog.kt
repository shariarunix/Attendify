package com.shariarunix.attendify.presentation.commons

import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.shariarunix.attendify.presentation.ui.theme.Blue80
import com.shariarunix.attendify.presentation.ui.theme.White80
import com.shariarunix.attendify.utils.SetDialogGravity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    state: TimePickerState,
    showTimePickerDialog: Boolean,
    onDismissRequest: () -> Unit
) {
    if(showTimePickerDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {

            SetDialogGravity(gravity = Gravity.BOTTOM)

            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(color = White80, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(
                    state = state,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TimePickerDefaults.colors(
                        periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.secondary
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onDismissRequest() },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Save",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = White80
                    )
                }
            }
        }
    }
}