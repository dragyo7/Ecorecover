package com.ecorecover.app.presentation.screens.appointments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecorecover.app.presentation.screens.appointments.components.*

@Composable
fun AppointmentScreen() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            AppointmentHeader()

            Spacer(modifier = Modifier.height(20.dp))

            AddressCard()

            Spacer(modifier = Modifier.height(20.dp))

            DateSelector()

            Spacer(modifier = Modifier.height(20.dp))

            TimeSelector()

            Spacer(modifier = Modifier.height(30.dp))

            ConfirmPickupButton()

            Spacer(modifier = Modifier.height(80.dp))

        }

    }

}