package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.MetalPrice
import com.ecorecover.app.data.remote.RetrofitClient

class MarketRepository {

    suspend fun getPrices(): Map<String, MetalPrice> {

        return RetrofitClient.api.getPrices()

    }

    suspend fun searchProducts(query: String) =
    RetrofitClient.api.searchProducts(query)

}