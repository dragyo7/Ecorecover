package com.ecorecover.app.data.model

data class EstimateResponse(
    val success: Boolean,
    val message: String,
    val data: EstimateData?
)