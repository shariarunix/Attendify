package com.shariarunix.attendify.presentation.ui.main.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shariarunix.attendify.R
import com.shariarunix.attendify.presentation.navigation.MainNavigation
import com.shariarunix.attendify.presentation.navigation.Screen

@Composable
fun MainScreens(
    modifier: Modifier = Modifier,
    appNavController: NavHostController
) {
    val navItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = R.drawable.ic_home,
            unselectedIcon = R.drawable.ic_home_inactive,
            screen = Screen.MainScreens.HomeScreen.route
        ),
        BottomNavigationItem(
            title = "All Class",
            selectedIcon = R.drawable.ic_classroom,
            unselectedIcon = R.drawable.ic_classroom_inactive,
            screen = Screen.MainScreens.AllClassScreen.route
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = R.drawable.ic_user,
            unselectedIcon = R.drawable.ic_user_inactive,
            screen = Screen.MainScreens.ProfileScreen.route
        ),
    )

    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = MaterialTheme.colorScheme.surfaceContainer)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .background(color = MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItems.forEach {
                        val isSelected = currentDestination?.hierarchy?.any { navDestination ->
                            navDestination.route == it.screen
                        } == true

                        BottomNavItem(isSelected, it, mainNavController)
                    }
                }
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            MainNavigation(appNavController, mainNavController)
        }
    }
}

@Composable
fun BottomNavItem(
    selected: Boolean,
    navItem: BottomNavigationItem,
    mainNavController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(64.dp)
            .width(94.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                if (!selected) {
                    mainNavController.navigate(navItem.screen) {
                        popUpTo(mainNavController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
    ) {
        Icon(
            painter = painterResource(
                id = if (selected) navItem.selectedIcon else navItem.unselectedIcon
            ),
            contentDescription = navItem.title,
            tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = navItem.title,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp
        )
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val screen: String
)