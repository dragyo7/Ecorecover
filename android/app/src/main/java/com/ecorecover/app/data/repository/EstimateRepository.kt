package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.EstimateRequest
import com.ecorecover.app.data.model.EstimateResponse
import com.ecorecover.app.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.ecorecover.app.data.remote.safeApiCall

class EstimateRepository {

    suspend fun getEstimate(product: String): EstimateResponse = withContext(Dispatchers.IO) {
        safeApiCall {
            RetrofitClient.api.estimate(
                EstimateRequest(product)
            )
        }
    }
}