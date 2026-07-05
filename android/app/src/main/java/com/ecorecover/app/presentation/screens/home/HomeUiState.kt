package com.ecorecover.app.presentation.screens.home

import com.ecorecover.app.data.model.MetalPrice
import com.ecorecover.app.data.model.AppointmentData

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val userName: String,
        val metalPrices: Map<String, MetalPrice>,
        val appointments: List<AppointmentData>
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
