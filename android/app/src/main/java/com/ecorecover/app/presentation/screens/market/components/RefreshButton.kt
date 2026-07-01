package com.ecorecover.app.presentation.screens.market.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun RefreshButton(
    onRefresh: () -> Unit = {}
) {

    IconButton(
        onClick = onRefresh
    ) {

        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Refresh"
        )

    }

}