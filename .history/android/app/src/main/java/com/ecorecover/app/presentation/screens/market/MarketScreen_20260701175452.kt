package com.ecorecover.app.presentation.screens.estimate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecorecover.app.presentation.common.ErrorScreen
import com.ecorecover.app.presentation.common.LoadingScreen
import com.ecorecover.app.presentation.screens.estimate.components.*

@Composable
fun EstimateScreen(
    viewModel: EstimateViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {

        uiState.isLoading -> {
            LoadingScreen()
        }

        uiState.error != null -> {
            ErrorScreen(
                message = uiState.error!!,
                onRetry = {
                    viewModel.loadEstimate("iPhone 13")
                }
            )
        }

        uiState.estimate != null -> {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    EstimateHeader()

                    Spacer(modifier = Modifier.height(24.dp))

                    val estimate = uiState.estimate!!

                    ValueCard(
                        value = estimate.estimated_recovery_value
                        )

                    Spacer(modifier = Modifier.height(20.dp))

                    MetalBreakdownCard(
                        metals = estimate.breakdown
                    )
                    
                    
                    Spacer(modifier = Modifier.height(20.dp))

                    RecoveryCard(
                        recoveryRate = estimate.recovery_rate
                )
                }
            }
        }
    }
}