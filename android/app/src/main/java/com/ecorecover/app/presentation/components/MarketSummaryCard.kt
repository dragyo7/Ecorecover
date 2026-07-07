package com.ecorecover.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MarketSummaryCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Today's Market",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            MarketRow("Gold", true, "₹12,215/g")
            MarketRow("Silver", false, "₹179/g")
            MarketRow("Palladium", true, "₹3,700/g")

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Highest Gainer",
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = "Gold (+0.92%)",
                color = Color(0xFF22C55E),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Updated just now",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

        }

    }

}

@Composable
private fun MarketRow(
    metal: String,
    up: Boolean,
    price: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector =
                if (up)
                    Icons.AutoMirrored.Filled.TrendingUp
                else
                    Icons.AutoMirrored.Filled.TrendingDown,

                contentDescription = null,

                tint =
                if (up)
                    Color(0xFF22C55E)
                else
                    Color.Red
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                metal,
                fontWeight = FontWeight.SemiBold
            )

        }

        Text(price)

    }

    Spacer(modifier = Modifier.height(12.dp))

}