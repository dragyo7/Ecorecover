package com.ecorecover.app.presentation.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Market : Screen("market")

    object Scan : Screen("scan")

    object History : Screen("history")

    object Profile : Screen("profile")

    object Estimate : Screen("estimate")

    object Appointment : Screen("appointment")

    object Login : Screen("login")
}