package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ecorecover.app.presentation.navigation.Screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.ecorecover.app.util.GlobalMessageBus
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showScaffoldUi = currentRoute != null && Screen.bottomBarItems.any { it?.route == currentRoute }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        launch {
            GlobalMessageBus.errors.collect { errorMsg ->
                snackbarHostState.showSnackbar(
                    message = errorMsg,
                    duration = SnackbarDuration.Short
                )
            }
        }
        launch {
            GlobalMessageBus.messages.collect { msg ->
                snackbarHostState.showSnackbar(
                    message = msg,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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