package com.shariarunix.attendify.presentation.ui.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.R
import com.shariarunix.attendify.domain.model.ClassModel
import com.shariarunix.attendify.presentation.commons.NoItemBox
import com.shariarunix.attendify.presentation.ui.main.home.components.ClassCard
import com.shariarunix.attendify.presentation.ui.main.home.components.CurrentClassCard
import com.shariarunix.attendify.presentation.ui.main.home.components.WelcomeBox
import com.shariarunix.attendify.presentation.ui.theme.AttendifyTheme
import com.shariarunix.attendify.presentation.ui.theme.RoundedCornerShape10Dp
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily
import com.shariarunix.attendify.utils.LOADING_CLASS_DATA
import kotlinx.coroutines.flow.MutableStateFlow

@Preview
@Composable
private fun HOmeScreenPreview() {
    val uiState = MutableStateFlow(
        HomeScreenState(
            greetingText = "Good Morning",
            userName = "Shariar",
            userProfileImage = null,
            currentClass = ClassModel(
                classID = "",
                classTitle = "Demo Class",
                classCode = "Class Code",
                classDescription = "Demo Class Note",
                classDate = "20/02/2003",
                classStartTime = "06:00 AM",
                classEndTime = "06:00 PM",
                classLocation = "Software Lab",
                classTeacherID = "",
                studentIDs = emptyList()
            ),
            isTeacher = true,
            isCurrentClassUpcoming = true,
            todayClasses = emptyList()
        )
    )

    AttendifyTheme {
        HomeScreen(uiState = uiState.collectAsState(),
            onEvent = {},
            gotoAddClassScreen = {},
            gotoAllClassScreen = {})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    uiState: State<HomeScreenState>,
    onEvent: (HomeScreenEvent) -> Unit,
    gotoAddClassScreen: () -> Unit,
    gotoAllClassScreen: () -> Unit
) {
    Scaffold { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                WelcomeBox(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                    greetingText = uiState.value.greetingText,
                    greetingIcon = when (uiState.value.greetingText) {
                        "Good Morning" -> Icons.Default.LightMode
                        "Good Afternoon" -> Icons.Default.LightMode
                        "Good Evening" -> Icons.Default.Nightlight
                        else -> Icons.Default.NightsStay
                    },
                    userName = uiState.value.userName,
                    userProfile = uiState.value.userProfileImage
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = MaterialTheme.colorScheme.surfaceContainer)
                )
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.background
                                )
                                .padding(16.dp)
                        ) {
                            CurrentClassCard(
                                classModel = uiState.value.currentClass ?: LOADING_CLASS_DATA,
                                isUpcoming = uiState.value.isCurrentClassUpcoming
                            ) {
                                /* TODO Show Class Detail */
                            }
                        }

                        /* If user is teacher then this section will be visible */
                        AnimatedVisibility(visible = uiState.value.isTeacher) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colorScheme.background
                                    )
                                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            ) {
                                Button(
                                    onClick = gotoAddClassScreen, // Opening Add Class Screen
                                    shape = RoundedCornerShape10Dp,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                ) {
                                    Text(
                                        text = "Add Class",
                                        fontSize = 20.sp,
                                        fontFamily = bodyFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                        )
                    }

                    stickyHeader {
                        StickyHeader(
                            text = "Upcoming Classes",
                            btnText = "View All",
                            onClick = gotoAllClassScreen
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (uiState.value.todayClasses.isEmpty()) {
                        item {
                            NoItemBox(message = "Looks like you have a free day today! Enjoy!")
                        }
                    } else {
                        items(uiState.value.todayClasses) {
                            ClassCard(classModel = it) {
                                /* TODO Show Class Detail Dialog */
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun StickyHeader(
    modifier: Modifier = Modifier, text: String, btnText: String, onClick: () -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            Row(modifier = Modifier
                .padding(end = 4.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    onClick()
                }
                .padding(horizontal = 6.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = btnText,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = btnText,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
        )
    }
}

