package com.ecorecover.app.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigate: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHomeData()
    }

    HomeContent(
        uiState = uiState,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refreshHomeData() },
        onNavigate = onNavigate
    )
}