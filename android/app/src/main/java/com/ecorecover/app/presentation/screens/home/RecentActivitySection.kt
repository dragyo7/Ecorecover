package com.ecorecover.app.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ecorecover.app.data.model.AppointmentData
import com.ecorecover.app.presentation.components.SectionTitle
import com.ecorecover.app.presentation.screens.orders.StatusBadge
import com.ecorecover.app.presentation.navigation.Screen

@Composable
fun RecentActivitySection(
    appointments: List<AppointmentData>,
    onNavigate: (String) -> Unit
) {
    SectionTitle(
        title = "Recent Activity",
        action = "See All",
        onActionClick = { onNavigate(Screen.Orders.route) }
    )

    Spacer(modifier = Modifier.height(12.dp))

    if (appointments.isEmpty()) {
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
            appointments.take(3).forEach { appointment ->
                ActivityCard(
                    appointment = appointment,
                    onClick = { onNavigate(Screen.Orders.route + "/${appointment.id}") }
                )
            }
        }
    }
}

@Composable
private fun ActivityCard(
    appointment: AppointmentData,
    onClick: () -> Unit
) {
    val icon = when {
        appointment.productName.contains("Laptop", ignoreCase = true) || appointment.productName.contains("MacBook", ignoreCase = true) -> Icons.Default.Laptop
        appointment.productName.contains("TV", ignoreCase = true) || appointment.productName.contains("Television", ignoreCase = true) -> Icons.Default.Tv
        else -> Icons.Default.PhoneAndroid
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = appointment.productName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = appointment.appointmentDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    StatusBadge(status = appointment.status)
                }
            }

            Text(
                text = "₹${String.format("%,.2f", appointment.estimatedPrice)}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}