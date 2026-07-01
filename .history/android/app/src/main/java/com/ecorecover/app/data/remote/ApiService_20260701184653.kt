package com.ecorecover.app.data.remote

import com.ecorecover.app.data.model.EstimateResponse
import com.ecorecover.app.data.model.PriceResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api/v1/prices/")
    suspend fun getPrices(): Map<String, PriceResponse>

    @GET("api/v1/search/")
    suspend fun searchProducts(
        @Query("q") query: String
    ): SearchResponse

    @POST("api/v1/estimate/")
    suspend fun estimate(
        @Body request: EstimateRequest
    ): EstimateResponse
}