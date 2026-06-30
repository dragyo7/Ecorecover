package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(

        floatingActionButton = {

            FloatingScanButton(
                navController = navController
            )

        },

        bottomBar = {

            BottomNavigationBar(
                navController = navController
            )

        }

    ) { padding ->

        content(padding)

    }

}