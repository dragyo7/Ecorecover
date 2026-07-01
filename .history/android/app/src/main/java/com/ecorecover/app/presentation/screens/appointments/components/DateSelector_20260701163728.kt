package com.ecorecover.app.presentation.screens.appointments.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun DateSelector() {

    var selected by remember {
        mutableStateOf("Tomorrow")
    }

    ExposedDropdownMenuBox(
        expanded = false,
        onExpandedChange = {}
    ) {

        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Pickup Date")
            }
        )

    }

}