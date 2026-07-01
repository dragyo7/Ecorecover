package com.ecorecover.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class MetalTickerItem(
    val name: String,
    val price: String,
    val change: Double
)

@Composable
fun MetalTicker() {

    val metals = listOf(
        MetalTickerItem("Gold", "₹12,215/g", 0.92),
        MetalTickerItem("Silver", "₹179/g", -1.20),
        MetalTickerItem("Palladium", "₹3,700/g", 2.10),
        MetalTickerItem("Rhodium", "₹23,571/g", 0.34),
        MetalTickerItem("Nickel", "₹1.56/g", 1.15)
    )

    Column {

        SectionTitle("Live Metal Market")

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(metals) { metal ->

                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier.width(170.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = metal.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = metal.price,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            val positive = metal.change >= 0

                            Icon(
                                imageVector =
                                if (positive)
                                    Icons.Default.TrendingUp
                                else
                                    Icons.Default.TrendingDown,
                                contentDescription = null,
                                tint =
                                if (positive)
                                    Color(0xFF22C55E)
                                else
                                    Color.Red
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "${metal.change}%",
                                color =
                                if (positive)
                                    Color(0xFF22C55E)
                                else
                                    Color.Red,
                                fontWeight = FontWeight.Bold
                            )

                        }

                    }

                }

            }

        }

    }

}