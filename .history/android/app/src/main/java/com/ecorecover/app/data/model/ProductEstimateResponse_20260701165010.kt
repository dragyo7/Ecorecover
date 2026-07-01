package com.ecorecover.app.data.model

data class ProductEstimateResponse(

    val product_name: String,

    val estimated_recovery_value: Double,

    val recovery_rate: Double,

    val breakdown: Map<String, Double>

)