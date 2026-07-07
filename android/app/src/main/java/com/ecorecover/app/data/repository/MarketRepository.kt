package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.MetalPrice
import com.ecorecover.app.data.model.SearchResponse
import com.ecorecover.app.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.ecorecover.app.data.remote.safeApiCall

class MarketRepository {

    suspend fun getPrices(): Map<String, MetalPrice> = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.getPrices() }
    }

    suspend fun searchProducts(query: String): SearchResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.searchProducts(query) }
    }
}