package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.EstimateRequest
import com.ecorecover.app.data.model.EstimateResponse
import com.ecorecover.app.data.remote.RetrofitClient

class EstimateRepository {

    suspend fun getEstimate(product: String): EstimateResponse {

        return RetrofitClient.api.estimate(
            EstimateRequest(product)
        )

    }

}