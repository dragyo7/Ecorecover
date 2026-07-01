package com.ecorecover.app.data.remote

import com.ecorecover.app.data.model.MetalPrice
import com.ecorecover.app.data.model.ProductEstimateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class EstimateRequest(
    val product: String
)

interface ApiService {

    @GET("api/v1/prices/")
    suspend fun getPrices(): Map<String, MetalPrice>

    @POST("api/v1/estimate/")
    suspend fun getEstimate(
        @Body request: EstimateRequest
    ): ProductEstimateResponse

}