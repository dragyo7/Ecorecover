package com.ecorecover.app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecorecover.app.presentation.components.AppScaffold
import com.ecorecover.app.presentation.screens.history.HistoryScreen
import com.ecorecover.app.presentation.screens.home.HomeScreen
import com.ecorecover.app.presentation.screens.market.MarketScreen
import com.ecorecover.app.presentation.screens.profile.ProfileScreen
import com.ecorecover.app.presentation.screens.scan.ScanScreen

@Composable
fun EcoRecoverApp() {

    val navController = rememberNavController()

    AppScaffold(navController = navController) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Home.route) {
                HomeScreen()
            }

            composable(Screen.Market.route) {
                MarketScreen()
            }

            composable(Screen.Scan.route) {
                ScanScreen()
            }

            composable(Screen.History.route) {
                HistoryScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}