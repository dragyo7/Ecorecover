package com.ecorecover.app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ecorecover.app.presentation.components.AppScaffold
import com.ecorecover.app.presentation.screens.auth.AuthViewModel
import com.ecorecover.app.presentation.screens.auth.LoginScreen
import com.ecorecover.app.presentation.screens.auth.SignupScreen
import com.ecorecover.app.presentation.screens.auth.VerificationScreen
import com.ecorecover.app.presentation.screens.history.HistoryScreen
import com.ecorecover.app.presentation.screens.home.HomeScreen
import com.ecorecover.app.presentation.screens.market.MarketScreen
import com.ecorecover.app.presentation.screens.profile.ProfileScreen
import com.ecorecover.app.presentation.screens.scan.ScanScreen
import com.ecorecover.app.data.repository.AuthRepository
import com.ecorecover.app.util.SessionManager

@Composable
fun EcoRecoverApp() {

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val authRepository = remember { AuthRepository() }
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(authRepository, sessionManager)
    )

    val navController = rememberNavController()

    // Determine startup destination based on stored session
    val startDestination = if (sessionManager.isLoggedIn()) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }

    AppScaffold(navController = navController) { padding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignup = {
                        navController.navigate(Screen.Signup.route)
                    },
                    onNavigateToVerification = { email ->
                        navController.navigate(Screen.Verification.createRoute(email))
                    }
                )
            }

            composable(Screen.Signup.route) {
                SignupScreen(
                    viewModel = authViewModel,
                    onSignupSuccess = { email ->
                        navController.navigate(Screen.Verification.createRoute(email)) {
                            popUpTo(Screen.Signup.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Signup.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                route = Screen.Verification.route,
                arguments = listOf(navArgument("email") { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                VerificationScreen(
                    viewModel = authViewModel,
                    email = email,
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Verification.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                if (!sessionManager.isLoggedIn()) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                } else {
                    HomeScreen()
                }
            }

            composable(Screen.Market.route) {
                if (!sessionManager.isLoggedIn()) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                } else {
                    MarketScreen()
                }
            }

            composable(Screen.Scan.route) {
                if (!sessionManager.isLoggedIn()) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                } else {
                    ScanScreen()
                }
            }

            composable(Screen.History.route) {
                if (!sessionManager.isLoggedIn()) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                } else {
                    HistoryScreen()
                }
            }

            composable(Screen.Profile.route) {
                if (!sessionManager.isLoggedIn()) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                } else {
                    ProfileScreen(
                        fullName = sessionManager.getFullName() ?: "EcoUser",
                        email = sessionManager.getEmail() ?: "",
                        onLogout = {
                            authViewModel.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}