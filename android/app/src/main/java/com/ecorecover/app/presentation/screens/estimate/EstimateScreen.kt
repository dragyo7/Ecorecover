package com.ecorecover.app.presentation.screens.estimate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ecorecover.app.presentation.common.ErrorScreen
import com.ecorecover.app.presentation.common.LoadingScreen
import com.ecorecover.app.presentation.screens.estimate.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstimateScreen(
    product: String,
    onNavigateBack: () -> Unit,
    onNavigateToPickup: () -> Unit,
    viewModel: EstimateViewModel
) {
    LaunchedEffect(product) {
        viewModel.loadEstimate(product)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Device Valuation Details") },
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
        when {
            uiState.isLoading -> {
                LoadingScreen()
            }
            uiState.error != null -> {
                ErrorScreen(
                    message = uiState.error!!,
                    onRetry = {
                        viewModel.loadEstimate(product)
                    }
                )
            }
            uiState.estimate != null -> {
                val estimate = uiState.estimate!!

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        // Device Title and Gradient Image Placeholder
                        DeviceVisualHeader(product = estimate.product)

                        Spacer(modifier = Modifier.height(20.dp))

                        // Estimated Value Card
                        ValueCard(
                            value = estimate.estimated_total_value_inr
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Recovery progress card (Recovery percentage)
                        RecoveryCard(
                            recoveryRate = 96.5
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Metal recovery breakdown
                        MetalBreakdownCard(
                            metals = estimate.metal_content_used
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Live metal prices used
                        LiveValuationDetailsCard(
                            valuation = estimate.metal_valuation
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // CO₂ saved & Circular economy contribution
                        val co2Saved = remember(estimate.product) {
                            calculateCo2Saved(estimate.product)
                        }
                        EnvironmentalImpactCard(
                            co2SavedKg = co2Saved,
                            circularEconomyScore = if (estimate.estimated_total_value_inr > 20000) "Excellent Yield" else "High Yield"
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Last updated timestamp
                        Text(
                            text = "Last updated: ${estimate.timestamp}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Continue to pickup action
                        PickupButton(
                            onClick = onNavigateToPickup
                        )

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
            else -> {
                ErrorScreen(
                    message = "Valuation data is unavailable for this device.",
                    onRetry = {
                        viewModel.loadEstimate(product)
                    }
                )
            }
        }
    }
}

@Composable
private fun DeviceVisualHeader(product: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Devices,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = product,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

private fun calculateCo2Saved(product: String): Double {
    val lower = product.lowercase()
    return when {
        lower.contains("macbook") || lower.contains("laptop") -> 4.8
        lower.contains("ipad") || lower.contains("tablet") -> 2.4
        lower.contains("iphone") || lower.contains("phone") -> 1.4
        lower.contains("battery") -> 2.1
        else -> 1.2
    }
}