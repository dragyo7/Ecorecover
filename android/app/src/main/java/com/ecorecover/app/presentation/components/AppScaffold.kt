package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ecorecover.app.presentation.navigation.Screen

@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showScaffoldUi = currentRoute != null && Screen.bottomBarItems.any { it?.route == currentRoute }

    Scaffold(
        floatingActionButton = {
            if (showScaffoldUi) {
                FloatingScanButton(
                    navController = navController
                )
            }
        },
        bottomBar = {
            if (showScaffoldUi) {
                BottomNavigationBar(
                    navController = navController
                )
            }
        }
    ) { padding ->
        content(padding)
    }
}