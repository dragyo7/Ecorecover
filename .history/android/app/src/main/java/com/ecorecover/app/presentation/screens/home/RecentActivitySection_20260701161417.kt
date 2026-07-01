package com.ecorecover.app.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ecorecover.app.presentation.components.SectionTitle

data class ActivityItem(
    val title: String,
    val value: String,
    val date: String
)

@Composable
fun RecentActivitySection() {

    val activities = listOf(

        ActivityItem(
            "iPhone 13",
            "₹4,520",
            "Today"
        ),

        ActivityItem(
            "Dell Laptop",
            "₹2,180",
            "Yesterday"
        ),

        ActivityItem(
            "Samsung TV",
            "₹860",
            "2 days ago"
        )

    )

    SectionTitle(
        title = "Recent Activity",
        action = "See All"
    )

    Spacer(modifier = Modifier.height(12.dp))

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        activities.forEach {

            ActivityCard(it)

        }

    }

}

@Composable
private fun ActivityCard(
    item: ActivityItem
) {

    Card {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(
                Icons.Default.PhoneAndroid,
                null
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    item.title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    item.date
                )

            }

            Text(
                item.value,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

        }

    }

}