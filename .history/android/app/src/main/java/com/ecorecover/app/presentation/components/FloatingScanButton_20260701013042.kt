package com.ecorecover.app.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ecorecover.app.presentation.navigation.Screen

@Composable
fun FloatingScanButton(
    navController: NavController
) {

    FloatingActionButton(
        onClick = {
            navController.navigate(Screen.Scan.route)
        },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {

        Icon(
            imageVector = Icons.Outlined.QrCodeScanner,
            contentDescription = "Scan Device"
        )

    }

}