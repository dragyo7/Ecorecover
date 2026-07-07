package com.ecorecover.app.presentation.screens.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecorecover.app.data.model.MetalPrice
import com.ecorecover.app.data.model.AppointmentData
import com.ecorecover.app.presentation.components.AppointmentCard
import androidx.compose.foundation.BorderStroke
import com.ecorecover.app.presentation.navigation.Screen
import com.ecorecover.app.presentation.screens.orders.StatusBadge
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onNavigate: (String) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(targetState = uiState, label = "homeStateTransition", modifier = Modifier.fillMaxSize()) { state ->
            when (state) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .verticalScroll(rememberScrollState())
                    ) {
                        HomeSkeleton()
                    }
                }
                is HomeUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) {
                        HomeErrorState(
                            message = state.message,
                            onRetry = onRefresh
                        )
                    }
                }
                is HomeUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 24.dp)
                    ) {
                        HomeSuccessState(
                            userName = state.userName,
                            metalPrices = state.metalPrices,
                            appointments = state.appointments,
                            onNavigate = onNavigate
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeSuccessState(
    userName: String,
    metalPrices: Map<String, MetalPrice>,
    appointments: List<AppointmentData>,
    onNavigate: (String) -> Unit
) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }

    // 1. HEADER SECTION
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "$greeting,",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Avatar bubble
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable { onNavigate(Screen.Profile.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName.firstOrNull()?.uppercase() ?: "U",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location & ETA banner row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Nagpur, Maharashtra",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Blinkit style speed banner
                val upcomingPickup = appointments.firstOrNull { it.status.lowercase() in listOf("pending", "confirmed", "scheduled") }
                val speedBannerText = if (upcomingPickup != null) "Pickup Active" else "No active pickup"
                val speedBannerBg = if (upcomingPickup != null) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                val speedBannerFg = if (upcomingPickup != null) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(speedBannerBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = speedBannerText,
                        color = speedBannerFg,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // 2. DISCOVERY SECTION
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        // Universal Search Bar (Clickable container)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { onNavigate(Screen.Search.route) },
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            border = ButtonDefaults.outlinedButtonBorder
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Search iPhone, Laptops, Batteries...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Horizontal Categories
        Text(
            text = "Categories to Recycle",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = listOf(
                "Phones" to "📱",
                "Laptops" to "💻",
                "Tablets" to "📠",
                "Accessories" to "🎧",
                "Batteries" to "🔋",
                "Appliances" to "📺"
            )
            categories.forEach { (name, emoji) ->
                SuggestionChip(
                    onClick = { onNavigate(Screen.Search.route + "?category=$name") },
                    label = { Text("$emoji $name") },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Rewards Summary
    val completed = appointments.filter { it.status.lowercase() == "completed" }
    val devicesRecycled = completed.size
    val moneyEarned = completed.sumOf { it.estimatedPrice }
    var co2Saved = 0.0
    for (a in completed) {
        val name = a.productName.lowercase()
        if (name.contains("phone") || name.contains("iphone")) {
            co2Saved += 1.4
        } else if (name.contains("laptop") || name.contains("macbook")) {
            co2Saved += 4.8
        } else {
            co2Saved += 2.5
        }
    }
    val ecoPoints = maxOf(devicesRecycled * 100 + (moneyEarned / 10).toInt(), 150)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.15f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🌱", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "My Green Impact",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                Text(
                    text = "View Leaderboard >",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigate(Screen.Rewards.route) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RewardStatItem(
                    value = "$ecoPoints",
                    label = "Eco Points",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                )
                RewardStatItem(
                    value = "$devicesRecycled",
                    label = "Recycled",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                )
                RewardStatItem(
                    value = "${String.format("%.1f", co2Saved)} kg",
                    label = "CO₂ Saved",
                    color = Color(0xFF22C55E),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    // Upcoming Pickup Card
    val activePickup = appointments.firstOrNull { it.status.lowercase() in listOf("pending", "confirmed", "scheduled") }
    if (activePickup != null) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable { onNavigate(Screen.Orders.route + "/${activePickup.id}") },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📅", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Upcoming Pickup",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    StatusBadge(status = activePickup.status)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = activePickup.productName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Date & Time Slot",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "${activePickup.appointmentDate} • ${activePickup.appointmentTime}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Est. Payout",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "₹${String.format("%.2f", activePickup.estimatedPrice)}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // 3. QUICK ACTIONS GRID
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            QuickActionCard(
                title = "Instant Quote",
                subtitle = "Value in 2 mins",
                icon = Icons.Outlined.Calculate,
                backgroundColor = Color(0xFF0EA5E9).copy(alpha = 0.1f),
                tintColor = Color(0xFF0EA5E9),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.Search.route) }
            )
            Spacer(modifier = Modifier.width(12.dp))
            QuickActionCard(
                title = "Schedule Pickup",
                subtitle = "Doorstep collection",
                icon = Icons.Outlined.LocalShipping,
                backgroundColor = Color(0xFF22C55E).copy(alpha = 0.1f),
                tintColor = Color(0xFF22C55E),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.Pickup.createRoute("iPhone 11")) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            QuickActionCard(
                title = "Track Pickup",
                subtitle = "Live tracking status",
                icon = Icons.Outlined.ReceiptLong,
                backgroundColor = Color(0xFF8B5CF6).copy(alpha = 0.1f),
                tintColor = Color(0xFF8B5CF6),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.Orders.route) }
            )
            Spacer(modifier = Modifier.width(12.dp))
            QuickActionCard(
                title = "Eco Rewards",
                subtitle = "Claim green coins",
                icon = Icons.Outlined.Stars,
                backgroundColor = Color(0xFFF59E0B).copy(alpha = 0.1f),
                tintColor = Color(0xFFF59E0B),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(Screen.Rewards.route) }
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // 4. LIVE METAL PRICES
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Live Metal Market",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Market API Connected",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            metalPrices.forEach { (name, metal) ->
                Card(
                    modifier = Modifier.width(150.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₹${String.format("%.2f", metal.price_inr_per_g)}/g",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val isUp = metal.daily_percent >= 0
                            Icon(
                                imageVector = if (isUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                                contentDescription = null,
                                tint = if (isUp) Color(0xFF22C55E) else Color.Red,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${String.format("%.2f", metal.daily_percent)}%",
                                color = if (isUp) Color(0xFF22C55E) else Color.Red,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // 5. CAROUSEL & OFFERS SECTION
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Popular Devices",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeaturedCard(
                title = "iPhone 11",
                subtitle = "Up to ₹18,500 value",
                savedCo2 = "🌱 1.4 kg CO₂ saved",
                gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
                onClick = { onNavigate(Screen.Estimate.createRoute("iPhone 11")) }
            )
            FeaturedCard(
                title = "MacBook Pro",
                subtitle = "Up to ₹82,000 value",
                savedCo2 = "🌱 4.8 kg CO₂ saved",
                gradientColors = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
                onClick = { onNavigate(Screen.Estimate.createRoute("MacBook Pro")) }
            )
            FeaturedCard(
                title = "OnePlus 9 Pro",
                subtitle = "Up to ₹12,000 value",
                savedCo2 = "🌱 2.1 kg CO₂ saved",
                gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669)),
                onClick = { onNavigate(Screen.Estimate.createRoute("OnePlus 9 Pro")) }
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // 6. PROMOTIONAL ECOHUB CARD
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "♻️ Nearby Certified Recycler",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Green Earth Solutions Nagpur • 2.4 km away",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ISO 14001 certified electronics recycling facility offering secure data destruction.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // 7. RECENT ACTIVITY SECTION
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        RecentActivitySection(
            appointments = appointments,
            onNavigate = onNavigate
        )
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    tintColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(96.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tintColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun FeaturedCard(
    title: String,
    subtitle: String,
    savedCo2: String,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(130.dp)
            .background(
                brush = Brush.linearGradient(colors = gradientColors),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = savedCo2,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun HomeSkeleton() {
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Column(modifier = Modifier.padding(20.dp)) {
        // Top banner skeleton
        SkeletonBox(alpha = alpha, modifier = Modifier.fillMaxWidth().height(80.dp))

        Spacer(modifier = Modifier.height(20.dp))

        // Search bar skeleton
        SkeletonBox(alpha = alpha, modifier = Modifier.fillMaxWidth().height(56.dp))

        Spacer(modifier = Modifier.height(20.dp))

        // Chips skeleton
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SkeletonBox(alpha = alpha, modifier = Modifier.width(90.dp).height(32.dp))
            SkeletonBox(alpha = alpha, modifier = Modifier.width(90.dp).height(32.dp))
            SkeletonBox(alpha = alpha, modifier = Modifier.width(90.dp).height(32.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2x2 grid skeletons
        Row {
            SkeletonBox(alpha = alpha, modifier = Modifier.weight(1f).height(96.dp))
            Spacer(modifier = Modifier.width(12.dp))
            SkeletonBox(alpha = alpha, modifier = Modifier.weight(1f).height(96.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            SkeletonBox(alpha = alpha, modifier = Modifier.weight(1f).height(96.dp))
            Spacer(modifier = Modifier.width(12.dp))
            SkeletonBox(alpha = alpha, modifier = Modifier.weight(1f).height(96.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Market skeleton title
        SkeletonBox(alpha = alpha, modifier = Modifier.width(180.dp).height(24.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SkeletonBox(alpha = alpha, modifier = Modifier.width(150.dp).height(100.dp))
            SkeletonBox(alpha = alpha, modifier = Modifier.width(150.dp).height(100.dp))
            SkeletonBox(alpha = alpha, modifier = Modifier.width(150.dp).height(100.dp))
        }
    }
}

@Composable
private fun SkeletonBox(alpha: Float, modifier: Modifier) {
    Box(
        modifier = modifier
            .alpha(alpha)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), shape = RoundedCornerShape(16.dp))
    )
}

@Composable
private fun HomeErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "⚠️ Network Connection Issue",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Retry Connection")
            }
        }
    }
}

@Composable
private fun RewardStatItem(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = color
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium
        )
    }
}