package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.EstimateResponse
import com.ecorecover.app.data.remote.EstimateRequest
import com.ecorecover.app.data.remote.RetrofitClient

class EstimateRepository {

    suspend fun estimate(product: String): EstimateResponse {
        return RetrofitClient.api.getEstimate(
            EstimateRequest(product)
        )
    }
}