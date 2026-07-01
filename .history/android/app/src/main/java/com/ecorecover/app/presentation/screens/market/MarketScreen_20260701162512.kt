package com.ecorecover.app.presentation.screens.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecorecover.app.presentation.screens.market.components.MarketHeader
import com.ecorecover.app.presentation.screens.market.components.MarketPriceItem
import com.ecorecover.app.presentation.screens.market.components.MarketSearchBar
import com.ecorecover.app.presentation.screens.market.components.MarketStatsCard
import com.ecorecover.app.presentation.screens.market.components.RefreshButton

data class MarketItem(
    val metal: String,
    val price: String,
    val change: Double
)

@Composable
fun MarketScreen() {

    val metals = listOf(
        MarketItem("Gold", "₹12,215/g", 0.92),
        MarketItem("Silver", "₹179/g", -1.20),
        MarketItem("Palladium", "₹3,700/g", 2.10),
        MarketItem("Rhodium", "₹23,571/g", 0.34),
        MarketItem("Copper", "₹0.91/g", -0.52),
        MarketItem("Nickel", "₹1.56/g", 1.15)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            // Header
            MarketHeader()

            Spacer(modifier = Modifier.height(20.dp))

            // Search
            MarketSearchBar()

            Spacer(modifier = Modifier.height(20.dp))

            // Today's Summary
            MarketStatsCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Refresh Button
            RefreshButton()

            Spacer(modifier = Modifier.height(20.dp))

            // Metal List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {

                items(metals) { metal ->

                    MetalPriceItem(
                        name = metal.metal,
                        price = metal.price,
                        change = metal.change
                    )

                }

            }

        }

    }

}