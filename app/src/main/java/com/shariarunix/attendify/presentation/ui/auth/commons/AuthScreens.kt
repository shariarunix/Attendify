package com.shariarunix.attendify.presentation.ui.auth.commons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.shariarunix.attendify.presentation.navigation.AuthNavigation
import com.shariarunix.attendify.presentation.commons.TopAppBar

@Composable
fun AuthScreens(
    modifier: Modifier = Modifier,
    appNavController: NavHostController,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar {
                onBackPressed()
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            AuthNavigation(appNavController)
        }

    }
}