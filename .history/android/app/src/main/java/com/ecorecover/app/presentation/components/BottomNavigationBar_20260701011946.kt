package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ecorecover.app.presentation.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    val currentDestination =
        navController.currentBackStackEntryAsState().value?.destination

    NavigationBar(

        modifier = Modifier
            .navigationBarsPadding()
            .height(82.dp),

        tonalElevation = 8.dp,

        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        )

    ) {

        Screen.bottomBarItems.forEach { screen ->

            val selected =
                currentDestination?.route == screen.route

            NavigationBarItem(

                selected = selected,

                onClick = {

                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.startDestinationId)

                        launchSingleTop = true

                        restoreState = true

                        saveState = true
                    }

                },

                icon = {

                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )

                },

                label = {

                    Text(screen.title)

                },

                alwaysShowLabel = true,

                colors = NavigationBarItemDefaults.colors()

            )

        }

    }

}