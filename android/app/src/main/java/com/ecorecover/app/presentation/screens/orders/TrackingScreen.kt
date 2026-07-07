package com.ecorecover.app.presentation.screens.orders

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecorecover.app.data.model.AppointmentData
import com.ecorecover.app.presentation.common.LoadingScreen
import com.ecorecover.app.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    orderId: String,
    onNavigateBack: () -> Unit,
    onNavigateToPayment: (String, Double) -> Unit,
    ordersViewModel: OrdersViewModel = viewModel()
) {
    val detailUiState by ordersViewModel.detailUiState.collectAsState()
    
    // Fetch order details
    LaunchedEffect(orderId) {
        ordersViewModel.loadOrderDetail(orderId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Live Pickup Tracking", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            when (val state = detailUiState) {
                is OrderDetailUiState.Loading -> LoadingScreen()
                is OrderDetailUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    }
                }
                is OrderDetailUiState.Success -> {
                    TrackingContent(
                        order = state.order,
                        onNavigateToPayment = onNavigateToPayment
                    )
                }
            }
        }
    }
}

@Composable
private fun TrackingContent(
    order: AppointmentData,
    onNavigateToPayment: (String, Double) -> Unit
) {
    // Animation progress from 0.0 to 1.0
    var progress by rememberSaveable { mutableStateOf(0.0f) }
    
    // Coroutine to simulate recycler truck movement along the path
    LaunchedEffect(Unit) {
        while (progress < 1.0f) {
            delay(150)
            progress += 0.015f
            if (progress > 1.0f) progress = 1.0f
        }
    }

    // Dynamic stats based on simulation progress
    val totalDistanceKm = 4.2
    val remainingDistance = maxOf(0.0, ((1.0f - progress) * totalDistanceKm * 10).roundToInt() / 10.0)
    val remainingEtaMins = (remainingDistance * 3.0).roundToInt()
    
    val currentStage = when {
        progress < 0.15f -> "Pickup Requested"
        progress < 0.35f -> "Recycler Assigned"
        progress < 0.65f -> "On the Way"
        progress < 0.85f -> "Arriving"
        progress < 0.95f -> "Pickup Complete"
        progress < 0.99f -> "Payment Processing"
        else -> "Paid"
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val secondaryColor = MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Google-Maps-Style Simulated Map Canvas (Flexible height)
        Box(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth()
                .background(Color(0xFFE2E8F0)) // Light gray map background
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                // Draw Nagpur green park circles/rects
                drawCircle(
                    color = Color(0xFFDCFCE7),
                    radius = 120f,
                    center = Offset(width * 0.2f, height * 0.3f)
                )
                drawRect(
                    color = Color(0xFFDCFCE7),
                    topLeft = Offset(width * 0.7f, height * 0.6f),
                    size = Size(180f, 150f)
                )

                // Draw Nagpur river/lake path (blue curvy line)
                val riverPath = Path().apply {
                    moveTo(0f, height * 0.8f)
                    cubicTo(width * 0.3f, height * 0.75f, width * 0.6f, height * 0.9f, width, height * 0.85f)
                }
                drawPath(
                    path = riverPath,
                    color = Color(0xFFE0F2FE),
                    style = Stroke(width = 30f)
                )

                // Draw major grid lines / streets (white lines)
                drawLine(Color.White, Offset(0f, height * 0.4f), Offset(width, height * 0.4f), strokeWidth = 20f)
                drawLine(Color.White, Offset(width * 0.5f, 0f), Offset(width * 0.5f, height), strokeWidth = 25f)
                drawLine(Color.White, Offset(0f, height * 0.15f), Offset(width, height * 0.8f), strokeWidth = 15f)

                // Draw route polyline (dotted primary line)
                val startPoint = Offset(width * 0.15f, height * 0.2f)  // Recycler Hub
                val midPoint = Offset(width * 0.5f, height * 0.4f)     // Junction
                val endPoint = Offset(width * 0.75f, height * 0.75f)   // User Location

                val polylinePath = Path().apply {
                    moveTo(startPoint.x, startPoint.y)
                    lineTo(midPoint.x, midPoint.y)
                    lineTo(endPoint.x, endPoint.y)
                }
                drawPath(
                    path = polylinePath,
                    color = Color(0xFF3B82F6),
                    style = Stroke(
                        width = 8f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                    )
                )

                // Draw Recycler Hub Pin (Green)
                drawCircle(color = Color(0xFF10B981), radius = 16f, center = startPoint)
                drawCircle(color = Color.White, radius = 6f, center = startPoint)

                // Draw User Home Pin (Red)
                drawCircle(color = Color(0xFFEF4444), radius = 18f, center = endPoint)
                drawCircle(color = Color.White, radius = 8f, center = endPoint)

                // Calculate current animated position of the truck
                val currentPos = when {
                    progress < 0.5f -> {
                        // Interpolate between start and mid
                        val t = progress / 0.5f
                        Offset(
                            startPoint.x + (midPoint.x - startPoint.x) * t,
                            startPoint.y + (midPoint.y - startPoint.y) * t
                        )
                    }
                    else -> {
                        // Interpolate between mid and end
                        val t = (progress - 0.5f) / 0.5f
                        Offset(
                            midPoint.x + (endPoint.x - midPoint.x) * t,
                            midPoint.y + (endPoint.y - midPoint.y) * t
                        )
                    }
                }

                // Draw pulsing accuracy circle at current position
                drawCircle(
                    color = Color(0x333B82F6),
                    radius = 40f + (progress * 10f) % 20f,
                    center = currentPos
                )
                // Draw Recycler Vehicle Indicator
                drawCircle(color = Color(0xFF3B82F6), radius = 22f, center = currentPos)
                drawCircle(color = Color.White, radius = 10f, center = currentPos)
            }

            // Floater banner for dynamic time and remaining distance
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsBike,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (remainingDistance > 0) "$remainingEtaMins mins ($remainingDistance km away)" else "Recycler arrived at destination",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // 2. Tracking Dashboard & Status Progress Info (Fixed height)
        Card(
            modifier = Modifier
                .weight(1.3f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title and Progress State
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Nagpur E-Waste Recycler",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Booking ID: #${order.id.take(8).uppercase()}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentStage,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }

                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Recycler Info card with Trust elements
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("♻️", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Green Solutions Nagpur Hub",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified Recycler",
                                tint = Color(0xFF22C55E),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "Lic: LIC-RECYCLE-NGP-2026-8801 • GST: 27AAAAA0000A1Z5 (Verified)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFF59E0B),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "4.8 (1,240 pickups) • Resp. <30m",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Interactive Call/Message buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Call Helpline", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Chat Agent", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom release payment card once pickup completes
                if (progress >= 0.85f) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "E-waste Collected Successfully!",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "The recycling agent has verified the items. You can now release the estimated payout of ₹${String.format("%.2f", order.estimatedPrice)} securely.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Button(
                                onClick = { onNavigateToPayment(order.id, order.estimatedPrice) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Payment, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Release Payout via Razorpay", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    // Linear progress indicator representing route completion
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Transit Progress", style = MaterialTheme.typography.labelSmall)
                            Text(text = "${(progress * 100).toInt()}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                        )
                    }
                }
            }
        }
    }
}
