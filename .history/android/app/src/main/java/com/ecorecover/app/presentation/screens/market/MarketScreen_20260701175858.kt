package com.ecorecover.app.presentation.screens.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecorecover.app.presentation.common.UiState
import com.ecorecover.app.presentation.screens.market.components.MarketHeader
import com.ecorecover.app.presentation.screens.market.components.MarketSearchBar
import com.ecorecover.app.presentation.screens.market.components.MarketStatsCard
import com.ecorecover.app.presentation.screens.market.components.MetalPriceItem
import com.ecorecover.app.presentation.screens.market.components.RefreshButton

@Composable
fun MarketScreen(
    viewModel: MarketViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            MarketHeader()

            Spacer(modifier = Modifier.height(20.dp))

            MarketSearchBar()

            Spacer(modifier = Modifier.height(20.dp))

            MarketStatsCard()

            Spacer(modifier = Modifier.height(20.dp))

            RefreshButton {
                viewModel.loadPrices()
            }

            Spacer(modifier = Modifier.height(20.dp))

            when (val state = uiState) {

                UiState.Loading -> {

                    CircularProgressIndicator()

                }

                is UiState.Error -> {

                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )

                }

                is UiState.Success -> {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {

                        items(state.data) { item ->

                            MetalPriceItem(
                                name = item.metal,
                                price = "${item.price} ${item.unit}",
                                change = item.daily_change
                            )

                        }

                    }

                }

            }

        }

    }

}