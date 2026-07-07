package com.ecorecover.app.presentation.screens.orders

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
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
    val context = LocalContext.current
    var progress by rememberSaveable { mutableStateOf(0.0f) }
    
    // Coroutine to simulate recycler truck movement along the path
    LaunchedEffect(progress) {
        if (progress < 1.0f) {
            delay(300)
            progress = (progress + 0.02f).coerceAtMost(1.0f)
        }
    }

    // Coordinates for Nagpur route
    val recyclerHub = LatLng(21.1200, 79.0500)
    val userHome = LatLng(21.1458, 79.0882)
    val routePoints = remember {
        listOf(
            recyclerHub,
            LatLng(21.1250, 79.0600),
            LatLng(21.1350, 79.0700),
            LatLng(21.1400, 79.0800),
            userHome
        )
    }

    // Interpolate Agent Position
    val currentPosition = remember(progress) {
        val segmentCount = routePoints.size - 1
        val scaledProgress = progress * segmentCount
        val segmentIndex = scaledProgress.toInt().coerceIn(0, segmentCount - 1)
        val segmentProgress = scaledProgress - segmentIndex
        val start = routePoints[segmentIndex]
        val end = routePoints[segmentIndex + 1]
        
        LatLng(
            start.latitude + (end.latitude - start.latitude) * segmentProgress,
            start.longitude + (end.longitude - start.longitude) * segmentProgress
        )
    }

    // Camera Position State
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentPosition, 13.5f)
    }

    // Smoothly pan camera as recycler moves
    LaunchedEffect(currentPosition) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(currentPosition, 13.5f),
            1000
        )
    }

    // Dynamic stats based on simulation progress
    val totalDistanceKm = 4.2
    val remainingDistance = maxOf(0.0, ((1.0f - progress) * totalDistanceKm * 10).roundToInt() / 10.0)
    val remainingEtaMins = (remainingDistance * 3.0).roundToInt()
    
    val currentStage = when {
        progress < 0.15f -> "Pickup Requested"
        progress < 0.35f -> "Recycler Assigned"
        progress < 0.65f -> "On The Way"
        progress < 0.80f -> "Near You"
        progress < 0.90f -> "Arrived"
        progress < 0.98f -> "Pickup Complete"
        else -> "Payment Released"
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Google Maps View
        Box(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = false,
                    compassEnabled = true
                )
            ) {
                // Recycler Agent Marker
                Marker(
                    state = MarkerState(position = currentPosition),
                    title = "Recycler Agent",
                    snippet = "On the way: $currentStage",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )

                // User Home Marker
                Marker(
                    state = MarkerState(position = userHome),
                    title = "Your Home",
                    snippet = "Nagpur Green Park Hub",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )

                // Recycler Hub Marker
                Marker(
                    state = MarkerState(position = recyclerHub),
                    title = "Nagpur Recycler Hub",
                    snippet = "Green Solutions Nagpur",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )

                // Polyline Route
                Polyline(
                    points = routePoints,
                    color = MaterialTheme.colorScheme.primary,
                    width = 12f
                )
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

            // Quick Map Control Floaters (Refresh / Share)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        progress = 0.0f
                        Toast.makeText(context, "Location updated. Tracking restarted.", Toast.LENGTH_SHORT).show()
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh GPS")
                }

                FloatingActionButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Track my EcoRecover Pickup")
                            putExtra(Intent.EXTRA_TEXT, "EcoRecover Live Tracking: My e-waste pickup (ID: #${order.id.take(8).uppercase()}) is at stage: $currentStage ($remainingDistance km away)!")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Live Tracking"))
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share Location")
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

                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Expandable Recycler Info card with Trust elements
                RecyclerVerificationCard()

                // Interactive Call/Message buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:+919876543210")
                            }
                            context.startActivity(dialIntent)
                        },
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
                        Text("Call Agent", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Opening secure chat room with Recycler Agent...", Toast.LENGTH_SHORT).show()
                        },
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
