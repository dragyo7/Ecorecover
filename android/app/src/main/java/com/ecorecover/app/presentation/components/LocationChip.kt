package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationChip() {

    AssistChip(

        onClick = {},

        label = {

            Text("Nagpur")

        },

        leadingIcon = {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null
            )

        },

        modifier = Modifier.padding(top = 12.dp)

    )

}