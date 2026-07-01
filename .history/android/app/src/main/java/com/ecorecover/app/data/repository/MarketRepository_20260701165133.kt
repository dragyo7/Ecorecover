package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.MetalPriceResponse
import com.ecorecover.app.data.remote.RetrofitClient

class MarketRepository {

    suspend fun getPrices(): List<MetalPriceResponse> {
        return RetrofitClient.api.getPrices()
    }

}