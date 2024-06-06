package com.shariarunix.attendify.presentation.ui.main.allClass

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.domain.model.ClassModel
import com.shariarunix.attendify.presentation.commons.NoItemBox
import com.shariarunix.attendify.presentation.commons.TopAppBar
import com.shariarunix.attendify.presentation.ui.main.allClass.components.ClassDetail
import com.shariarunix.attendify.presentation.ui.main.home.components.ClassCard
import com.shariarunix.attendify.presentation.ui.theme.AttendifyTheme
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily
import com.shariarunix.attendify.utils.LOADING_CLASS_DATA
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AllClassScreenPreview() {
    val uiState = MutableStateFlow(
        AllClassScreenState(
            isUpcoming = true,
            startDate = 1,
            endDate = 15,
            selectedDateForClasses = 1,
            currentClass = ClassModel(
                classID = "",
                classTitle = "Demo Class",
                classCode = "Class Code",
                classDescription = "Demo Class Note",
                classDate = "20/02/2003",
                classStartTime = "06:00 AM",
                classEndTime = "06:00 PM",
                classLocation = "Gouripur",
                classTeacherID = "",
                studentIDs = emptyList()
            ),
        )
    )
    
    AttendifyTheme {
        AllClassScreen(
            uiState = uiState.collectAsState(),
            onEvent = {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllClassScreen(
    uiState: State<AllClassScreenState>,
    onEvent: (AllClassScreenEvent) -> Unit,
) {

    Scaffold { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                stickyHeader {
                    TopAppBar(
                        title = if (uiState.value.isUpcoming) "Upcoming Class" else "Ongoing Class",
                        titleColor = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        showIcon = false,
                        buttonIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cross),
                                contentDescription = "Cancel button",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .rotate(45f)
                                    .padding(4.dp),
                            )
                        },
                        onButtonClick = {
                            // TODO()
                        }
                    )
                }

                item {
                    ClassDetail(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp),
                        classModel = uiState.value.currentClass ?: LOADING_CLASS_DATA,
                        isUpcoming = uiState.value.isUpcoming
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    )
                }

                stickyHeader {
                    DateHeader(
                        startDate = uiState.value.startDate ?: 1,
                        endDate = uiState.value.endDate ?: 15,
                        selectedDate = uiState.value.selectedDateForClasses
                            ?: uiState.value.startDate ?: 1,
                    ) {
                        onEvent(AllClassScreenEvent.OnSelectedDateChange(it))
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (uiState.value.classesByDate.isEmpty()) {
                    item {
                        NoItemBox(
                            message = if (uiState.value.startDate == uiState.value.selectedDateForClasses) {
                                "Looks like you have a free day today! Enjoy!"
                            } else {
                                "You don't have any classes scheduled on this day."
                            }
                        )
                    }
                } else {
                    items(uiState.value.classesByDate) {
                        ClassCard(classModel = it) {
                            /* TODO Show Class Detail Dialog */
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateHeader(
    startDate: Int,
    endDate: Int,
    selectedDate: Int,
    onClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items((startDate..endDate).toList()) {
            if (it == startDate) {
                Spacer(modifier = Modifier.width(12.dp))
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(
                        color = if (it == selectedDate) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Unspecified
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable {
                        onClick(it)
                    }
            ) {
                Text(
                    text = if (startDate == it) "Today" else if (it < 10) "0$it" else "$it",
                    fontSize = 24.sp,
                    fontFamily = displayFontFamily,
                    color = if (it == selectedDate) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                    },
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 14.dp)
                )
            }
            if (it == endDate) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}