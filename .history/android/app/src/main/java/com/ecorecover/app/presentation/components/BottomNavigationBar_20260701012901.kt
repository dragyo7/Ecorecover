package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ecorecover.app.presentation.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.navigationBarsPadding()
    ) {

        Screen.bottomBarItems.forEach { screen ->

            NavigationBarItem(

                selected = currentRoute == screen.route,

                onClick = {

                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
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

                colors = NavigationBarItemDefaults.colors()

            )

        }

    }

}