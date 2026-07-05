package com.ecorecover.app.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Tv
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ecorecover.app.data.model.AppointmentData
import com.ecorecover.app.presentation.components.SectionTitle

data class ActivityItem(
    val title: String,
    val value: String,
    val date: String
)

@Composable
fun RecentActivitySection(
    appointments: List<AppointmentData>
) {
    val activities = appointments.take(3).map { appointment ->
        ActivityItem(
            title = appointment.productName,
            value = "₹${String.format("%,.2f", appointment.estimatedPrice)}",
            date = appointment.appointmentDate
        )
    }

    SectionTitle(
        title = "Recent Activity",
        action = "See All"
    )

    Spacer(modifier = Modifier.height(12.dp))

    if (activities.isEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No recent pickups scheduled",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            activities.forEach {
                ActivityCard(it)
            }
        }
    }
}

@Composable
private fun ActivityCard(
    item: ActivityItem
) {
    val icon = when {
        item.title.contains("Laptop", ignoreCase = true) || item.title.contains("MacBook", ignoreCase = true) -> Icons.Default.Laptop
        item.title.contains("TV", ignoreCase = true) || item.title.contains("Television", ignoreCase = true) -> Icons.Default.Tv
        else -> Icons.Default.PhoneAndroid
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }

            Text(
                text = item.value,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}