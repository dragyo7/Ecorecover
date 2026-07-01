package com.ecorecover.app.presentation.screens.market

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecorecover.app.presentation.screens.market.components.*

@Composable
fun MarketScreen(
    viewModel: MarketViewModel = viewModel()
) {

    val prices by viewModel.prices.collectAsState()

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

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                items(prices) { item ->

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