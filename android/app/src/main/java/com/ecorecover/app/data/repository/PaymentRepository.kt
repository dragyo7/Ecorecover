package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.*
import com.ecorecover.app.data.remote.RetrofitClient
import com.ecorecover.app.data.remote.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentRepository {
    suspend fun createPaymentOrder(appointmentId: String, amount: Double): PaymentOrderResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.createPaymentOrder(PaymentOrderRequest(appointmentId, amount)) }
    }

    suspend fun verifyPayment(request: PaymentVerifyRequest): PaymentVerifyResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.verifyPayment(request) }
    }

    suspend fun getTransactions(): TransactionResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.getTransactions() }
    }
}
