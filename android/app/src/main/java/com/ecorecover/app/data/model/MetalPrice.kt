package com.ecorecover.app.data.model

data class MetalPrice(

    val price_original: Double,

    val unit: String,

    val price_inr_per_g: Double,

    val price_inr_per_10g: Double,

    val price_inr_per_100g: Double,

    val price_inr_per_kg: Double,

    val daily_change: Double,

    val daily_percent: Double,

    val weekly_percent: Double,

    val monthly_percent: Double,

    val ytd_percent: Double,

    val yoy_percent: Double,

    val volatility: Double,

    val trend: String,

    val usd_to_inr: Double,

    val cny_to_inr: Double,

    val timestamp: String

)