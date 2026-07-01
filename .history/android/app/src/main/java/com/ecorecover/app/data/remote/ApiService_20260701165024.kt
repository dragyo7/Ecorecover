package com.ecorecover.app.data.remote

import com.ecorecover.app.data.model.MetalPriceResponse
import com.ecorecover.app.data.model.ProductEstimateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("prices")
    suspend fun getPrices(): List<MetalPriceResponse>

    @GET("product/{name}")
    suspend fun getProductEstimate(
        @Path("name") name: String
    ): ProductEstimateResponse

}