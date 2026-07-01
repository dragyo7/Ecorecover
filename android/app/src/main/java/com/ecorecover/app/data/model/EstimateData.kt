package com.ecorecover.app.data.model

data class EstimateData(

    val timestamp: String,

    val product: String,

    val entries_found: Int,

    val calculation_method: String,

    val estimated_total_value_inr: Double,

    val metal_contribution_percent: Map<String, Double>,

    val metal_content_used: Map<String, Double>,

    val metal_valuation: Map<String, MetalValuation>

)