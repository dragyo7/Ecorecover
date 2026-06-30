package com.ecorecover.app.presentation.navigation

sealed class Screen(
    val route: String,
    val title: String
) {

    data object Home : Screen(
        "home",
        "Home"
    )

    data object Market : Screen(
        "market",
        "Market"
    )

    data object Scan : Screen(
        "scan",
        "Scan"
    )

    data object History : Screen(
        "history",
        "History"
    )

    data object Profile : Screen(
        "profile",
        "Profile"
    )
}