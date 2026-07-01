package com.ecorecover.app.presentation.screens.estimate

import com.ecorecover.app.data.model.ProductEstimateResponse

data class EstimateUiState(

    val isLoading: Boolean = false,

    val estimate: ProductEstimateResponse? = null,

    val error: String? = null

)