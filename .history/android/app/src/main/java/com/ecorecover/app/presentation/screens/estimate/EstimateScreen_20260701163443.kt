package com.ecorecover.app.presentation.screens.estimate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecorecover.app.presentation.screens.estimate.components.EstimateHeader
import com.ecorecover.app.presentation.screens.estimate.components.ValueCard

import com.ecorecover.app.presentation.screens.estimate.components.MetalBreakdownCard
import com.ecorecover.app.presentation.screens.estimate.components.RecoveryCard
import com.ecorecover.app.presentation.screens.estimate.components.PickupButton

@Composable
fun EstimateScreen() {

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

        ValueCard()

        Spacer(modifier = Modifier.height(20.dp))

        MetalBreakdownCard()

        Spacer(modifier = Modifier.height(20.dp))

        RecoveryCard()

        Spacer(modifier = Modifier.height(24.dp))

        PickupButton()

        Spacer(modifier = Modifier.height(80.dp))

        }

    }

}