package com.shariarunix.attendify.presentation.ui.addClassScreen.dialog

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shariarunix.attendify.presentation.ui.theme.Blue80
import com.shariarunix.attendify.presentation.ui.theme.Gray40
import com.shariarunix.attendify.presentation.ui.theme.Gray80
import com.shariarunix.attendify.presentation.ui.theme.White40
import com.shariarunix.attendify.presentation.ui.theme.White80
import com.shariarunix.attendify.utils.SetDialogGravity

@Composable
fun RepeatClassDialog(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    repeatOptionList: List<String>,
    onDismissRequest: () -> Unit,
    onItemSelected: (Int) -> Unit,
) {
    if (isShow) {
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
                    .padding(16.dp)
            ) {

                Text(
                    text = "Select Repeated Option",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = Blue80
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = White40, shape = RoundedCornerShape(12.dp))
                        .clip(shape = RoundedCornerShape(12.dp))
                ) {
                    repeatOptionList.forEachIndexed { index, repeatedOption ->
                        Text(
                            text = repeatedOption,
                            fontSize = 18.sp,
                            color = Gray80,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onItemSelected(index)
                                }
                                .padding(16.dp, vertical = 16.dp)
                        )
                        if (index != repeatOptionList.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.5.dp)
                                    .background(color = Gray40)
                            )
                        }
                    }

                }

            }
        }
    }
}