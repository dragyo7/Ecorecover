package com.ecorecover.app.presentation.screens.estimate

import com.ecorecover.app.data.model.EstimateResponse

data class EstimateUiState(

    val loading: Boolean = false,

    val estimate: EstimateResponse? = null,

    val error: String? = null

)