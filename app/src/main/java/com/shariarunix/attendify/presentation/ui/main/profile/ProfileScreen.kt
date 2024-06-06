package com.shariarunix.attendify.presentation.ui.main.profile

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shariarunix.attendify.R
import com.shariarunix.attendify.domain.model.UserModel
import com.shariarunix.attendify.presentation.commons.SwitchWithCustomColors
import com.shariarunix.attendify.presentation.commons.TopAppBar
import com.shariarunix.attendify.presentation.ui.main.profile.dialog.UserInfoChangeDialog
import com.shariarunix.attendify.presentation.ui.theme.AttendifyTheme
import com.shariarunix.attendify.presentation.ui.theme.RoundedCornerShape10Dp
import com.shariarunix.attendify.presentation.ui.theme.bodyFontFamily
import com.shariarunix.attendify.presentation.ui.theme.displayFontFamily
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    val uiState = MutableStateFlow(
        ProfileScreenState(
            userData = UserModel(
                userID = "1",
                userName = "Shariar Nafiz",
                userEmail = "shariarunix@gmail.com",
                createdAt = "21 May, 2024"
            )
        )
    )
    AttendifyTheme {
        ProfileScreen(
            uiState = uiState.collectAsState(),
            onEvent = {},
            onLogoutClick = {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    uiState: State<ProfileScreenState>,
    onEvent: (ProfileScreenEvent) -> Unit,
    onLogoutClick: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Profile",
                titleColor = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                showIcon = false,
                buttonIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "Logout",
                    )
                }
            ) {
                // Show Notification TODO
            }
        },
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {

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
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = uiState.value.userData?.userProfileImage,
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(96.dp)
                                .clip(shape = CircleShape),
                            placeholder = painterResource(id = R.drawable.profile_placeholder),
                            error = painterResource(id = R.drawable.profile_placeholder),
                            contentScale = ContentScale.Crop
                        )
                    }
                } // Profile Image Section

                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.value.userData?.userName ?: "Loading",
                            fontSize = 28.sp,
                            fontFamily = displayFontFamily,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (uiState.value.userData?.teacher == true) "Teacher" else "Student",
                            fontFamily = bodyFontFamily,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceContainer
                    )
                } // Name Section

                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape10Dp
                            )
                            .clip(shape = RoundedCornerShape10Dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Dynamic Theme",
                                    fontFamily = bodyFontFamily,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                SwitchWithCustomColors(
                                    checked = uiState.value.isDynamicTheme,
                                    onCheckedChange = {
                                        onEvent(ProfileScreenEvent.OnDynamicThemeChange(it))
                                    }
                                )
                            }
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surfaceContainer
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Dark Theme",
                                fontFamily = bodyFontFamily,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            SwitchWithCustomColors(
                                checked = uiState.value.isDarkTheme,
                                onCheckedChange = {
                                    onEvent(ProfileScreenEvent.OnDarkThemeChange(it))
                                }
                            )
                        }
                    }
                } // Theme Section

                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 12.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape10Dp
                            )
                            .clip(shape = RoundedCornerShape10Dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "User Information",
                            fontFamily = bodyFontFamily,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Change",
                            fontFamily = bodyFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    onEvent(ProfileScreenEvent.IsUserInfoChangeDialogShow(true))
                                }
                                .padding(4.dp)
                        )
                    }
                } // User Info Title Section

                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape10Dp
                            )
                            .clip(shape = RoundedCornerShape10Dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        UserInfoItem(
                            identifier = "Name",
                            information = uiState.value.userData?.userName ?: "Loading"
                        ) // Name Section

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )
                        UserInfoItem(
                            identifier = "Email",
                            information = uiState.value.userData?.userEmail ?: "Loading"
                        ) // Email Section

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )
                        UserInfoItem(
                            identifier = "Phone",
                            information = uiState.value.userData?.userPhone ?: "Loading"
                        ) // Phone Section

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )
                        UserInfoItem(
                            identifier = "Role",
                            information = if (uiState.value.userData?.teacher == true) "Teacher" else "Student",
                        ) // Role Section

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )

                        UserInfoItem(
                            identifier = "Joined On",
                            information = uiState.value.userData?.createdAt ?: "Loading",
                        )
                    }
                } // User Info Section

                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape10Dp
                            )
                            .clip(shape = RoundedCornerShape10Dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Change Password",
                                fontFamily = bodyFontFamily,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Change",
                                fontFamily = bodyFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable {
                                        // TODO Change Password
                                    }
                                    .padding(4.dp)
                            )
                        }
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceContainer
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Delete Account",
                                fontFamily = bodyFontFamily,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Delete",
                                fontFamily = bodyFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable {
                                        // TODO Delete Account
                                    }
                                    .padding(4.dp)
                            )
                        }
                    }
                } // Change Password & Delete Account Button Section

                item {
                    Button(
                        onClick = {
                            onEvent(ProfileScreenEvent.OnLogOut)
                        },
                        shape = RoundedCornerShape10Dp,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Logout,
                            contentDescription = "Logout",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Log Out",
                            fontSize = 20.sp,
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                } // Log Out Button Section
            } // Lazy Column End
        } // Surface End

        UserInfoChangeDialog(
            uiState = uiState.value,
            onEvent = onEvent,
        ) {
            onEvent(ProfileScreenEvent.IsUserInfoChangeDialogShow(false))
        }
    }
}

@Composable
fun UserInfoItem(
    modifier: Modifier = Modifier,
    identifier: String,
    information: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = identifier,
            fontFamily = bodyFontFamily,
            fontSize = 18.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(0.3F)
        )
        Text(
            text = " : ",
            fontFamily = bodyFontFamily,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = information,
            fontFamily = bodyFontFamily,
            fontSize = 18.sp,
            maxLines = 1,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85F),
            modifier = Modifier.weight(1F)
        )
    }
}