package com.ecorecover.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun Header() {

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = when {

        hour < 12 -> "Good Morning"

        hour < 17 -> "Good Afternoon"

        else -> "Good Evening"

    }

    Column {

        Text(
            text = greeting,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "EcoRecover",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

    }

}