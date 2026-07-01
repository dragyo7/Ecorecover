package com.ecorecover.app.presentation.screens.appointments.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable

@Composable
fun TimeSelector() {

    var time by remember {
        mutableStateOf("10:00 AM")
    }

    OutlinedTextField(
        value = time,
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        label = {
            Text("Pickup Time")
        }
    )

}