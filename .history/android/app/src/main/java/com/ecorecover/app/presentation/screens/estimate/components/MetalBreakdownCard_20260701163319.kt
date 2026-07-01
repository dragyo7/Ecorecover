package com.ecorecover.app.presentation.screens.estimate.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class MetalRowData(
    val name: String,
    val amount: String,
    val color: Color
)

@Composable
fun MetalBreakdownCard() {

    val metals = listOf(
        MetalRowData("Gold", "0.032 g", Color(0xFFFFC107)),
        MetalRowData("Silver", "0.210 g", Color(0xFFB0BEC5)),
        MetalRowData("Copper", "18.4 g", Color(0xFFD97706)),
        MetalRowData("Aluminium", "12.8 g", Color(0xFF90CAF9))
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Metal Breakdown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            metals.forEach {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "● ${it.name}",
                        color = it.color,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = it.amount,
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier = Modifier.height(12.dp))
            }

        }

    }

}