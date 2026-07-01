package com.ecorecover.app.presentation.screens.home

import com.ecorecover.app.presentation.components.MetalTicker
import com.ecorecover.app.presentation.screens.home.CategoryGrid
import com.ecorecover.app.presentation.components.MarketSummaryCard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import com.ecorecover.app.presentation.components.Header
import com.ecorecover.app.presentation.components.LocationChip
import com.ecorecover.app.presentation.components.SearchBar
import com.ecorecover.app.presentation.components.HeroCard

@Composable
fun HomeContent(
    paddingValues: PaddingValues
) {

    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)

            .padding(paddingValues)

            .padding(20.dp)

            .verticalScroll(rememberScrollState())

    ) {

        Header()

        LocationChip()

        SearchBar()

        HeroCard()

        Spacer(modifier = Modifier.height(24.dp))

        MetalTicker()

        Spacer(modifier = Modifier.height(28.dp))

        CategoryGrid()

    }

}