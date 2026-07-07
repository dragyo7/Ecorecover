package com.ecorecover.app.data.remote

import com.ecorecover.app.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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

    @POST("api/v1/appointments/")
    suspend fun createAppointment(
        @Body request: AppointmentRequest
    ): AppointmentCreateResponse

    @GET("api/v1/appointments/")
    suspend fun getAppointments(): AppointmentResponse

    @GET("api/v1/orders/")
    suspend fun getOrders(): AppointmentResponse

    @GET("api/v1/orders/{id}")
    suspend fun getOrderDetail(
        @Path("id") orderId: String
    ): OrderDetailResponse

    @GET("api/v1/rewards/")
    suspend fun getRewards(): RewardsResponse

    @GET("api/v1/profile/")
    suspend fun getProfile(): ProfileResponse

    @PUT("api/v1/profile/")
    suspend fun updateProfile(
        @Body request: ProfileUpdateRequest
    ): ProfileResponse

    @POST("api/v1/payments/create-order")
    suspend fun createPaymentOrder(
        @Body request: PaymentOrderRequest
    ): PaymentOrderResponse

    @POST("api/v1/payments/verify")
    suspend fun verifyPayment(
        @Body request: PaymentVerifyRequest
    ): PaymentVerifyResponse

    @GET("api/v1/payments/transactions")
    suspend fun getTransactions(): TransactionResponse
}