package com.ecorecover.app.presentation.screens.estimate

import com.ecorecover.app.data.model.EstimateData

data class EstimateUiState(

    val isLoading: Boolean = false,

    val estimate: EstimateData? = null,

    val error: String? = null

)