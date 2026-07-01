package com.ecorecover.app.presentation.screens.appointments.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun DateSelector() {

    var date by remember {
        mutableStateOf("Tomorrow")
    }

    OutlinedTextField(
        value = date,
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        label = {
            Text("Pickup Date")
        }
    )
}