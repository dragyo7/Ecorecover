package com.ecorecover.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Home : Screen(
        "home",
        "Home",
        Icons.Outlined.Home
    )

    object Market : Screen(
        "market",
        "Market",
        Icons.Outlined.ShowChart
    )

    object Scan : Screen(
        "scan",
        "Scan",
        Icons.Outlined.QrCodeScanner
    )

    object History : Screen(
        "history",
        "History",
        Icons.Outlined.History
    )

    object Profile : Screen(
        "profile",
        "Profile",
        Icons.Outlined.Person
    )

    object Login : Screen(
        "login",
        "Login",
        Icons.Outlined.Person
    )

    object Signup : Screen(
        "signup",
        "Signup",
        Icons.Outlined.Person
    )

    object Verification : Screen(
        "verification/{email}",
        "Verification",
        Icons.Outlined.Person
    ) {
        fun createRoute(email: String) = "verification/$email"
    }

    companion object {

        val bottomBarItems = listOf(
            Home,
            Market,
            History,
            Profile
        )
    }
}