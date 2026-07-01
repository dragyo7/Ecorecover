package com.ecorecover.app.data.model

data class MetalPriceResponse(
    val metal: String,
    val price: Double,
    val unit: String,
    val daily_change: Double,
    val daily_percent: String
)