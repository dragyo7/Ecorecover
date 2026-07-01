package com.ecorecover.app.presentation.screens.estimate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecorecover.app.presentation.common.ErrorScreen
import com.ecorecover.app.presentation.common.LoadingScreen
import com.ecorecover.app.presentation.screens.estimate.components.EstimateHeader
import com.ecorecover.app.presentation.screens.estimate.components.MetalBreakdownCard
import com.ecorecover.app.presentation.screens.estimate.components.ValueCard

@Composable
fun EstimateScreen(
    viewModel: EstimateViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {

        uiState.loading -> {

            LoadingScreen()

        }

        uiState.error != null -> {

            ErrorScreen(

                message = uiState.error!!,

                onRetry = {

                    viewModel.loadEstimate("Iphone")

                }

            )

        }

        uiState.estimate != null -> {

            val response = uiState.estimate!!

            val estimate = response.data

            Surface(

                modifier = Modifier.fillMaxSize(),

                color = MaterialTheme.colorScheme.background

            ) {

                Column(

                    modifier = Modifier

                        .fillMaxSize()

                        .padding(20.dp)

                        .verticalScroll(rememberScrollState())

                ) {

                    EstimateHeader()

                    Spacer(modifier = Modifier.height(24.dp))

                    ValueCard(

                        value = estimate.estimated_total_value_inr

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    MetalBreakdownCard(

                        metals = estimate.metal_contribution_percent

                    )

                }

            }

        }

    }

}