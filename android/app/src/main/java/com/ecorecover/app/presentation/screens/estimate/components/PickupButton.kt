package com.ecorecover.app.presentation.screens.estimate.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PickupButton(onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {

        Icon(
            Icons.Default.LocalShipping,
            contentDescription = null
        )

        Text("  Schedule Pickup")

    }

}