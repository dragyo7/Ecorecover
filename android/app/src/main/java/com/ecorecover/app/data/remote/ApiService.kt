package com.ecorecover.app.data.remote

import com.ecorecover.app.data.model.EstimateRequest
import com.ecorecover.app.data.model.EstimateResponse
import com.ecorecover.app.data.model.MetalPrice
import com.ecorecover.app.data.model.SearchResponse
import com.ecorecover.app.data.model.SignupRequest
import com.ecorecover.app.data.model.SignupResponse
import com.ecorecover.app.data.model.LoginRequest
import com.ecorecover.app.data.model.LoginResponse
import com.ecorecover.app.data.model.ResendRequest
import com.ecorecover.app.data.model.ResendResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api/v1/prices/")
    suspend fun getPrices(): Map<String, MetalPrice>

    @GET("api/v1/search/")
    suspend fun searchProducts(
        @Query("q") query: String
    ): SearchResponse

    @POST("api/v1/estimate/")
    suspend fun estimate(
        @Body request: EstimateRequest
    ): EstimateResponse

    @POST("api/v1/auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse

    @POST("api/v1/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/v1/auth/resend")
    suspend fun resend(
        @Body request: ResendRequest
    ): ResendResponse
}