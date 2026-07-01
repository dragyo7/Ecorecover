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

        }

    }

}