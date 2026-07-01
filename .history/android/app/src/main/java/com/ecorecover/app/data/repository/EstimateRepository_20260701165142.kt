package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.ProductEstimateResponse
import com.ecorecover.app.data.remote.RetrofitClient

class EstimateRepository {

    suspend fun getEstimate(
        productName: String
    ): ProductEstimateResponse {

        return RetrofitClient.api.getProductEstimate(productName)

    }

}