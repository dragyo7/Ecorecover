package com.ecorecover.app.data.repository

import android.util.Log
import com.ecorecover.app.data.model.AppointmentRequest
import com.ecorecover.app.data.model.AppointmentResponse
import com.ecorecover.app.data.model.AppointmentCreateResponse
import com.ecorecover.app.data.model.OrderDetailResponse
import com.ecorecover.app.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.ecorecover.app.data.remote.safeApiCall

class AppointmentRepository {

    suspend fun createAppointment(request: AppointmentRequest): AppointmentCreateResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.createAppointment(request) }
    }

    suspend fun getAppointments(): AppointmentResponse = withContext(Dispatchers.IO) {
        Log.d("RepoTrace", "Repository.getAppointments() entered")
        val resp = safeApiCall { RetrofitClient.api.getAppointments() }
        Log.d("RepoTrace", "Repository received response success=${resp.success}, dataSize=${resp.data?.size}")
        resp
    }

    suspend fun getOrders(): AppointmentResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.getOrders() }
    }

    suspend fun getOrderDetail(orderId: String): OrderDetailResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.getOrderDetail(orderId) }
    }
}
