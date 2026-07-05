package com.ecorecover.app.data.model

import com.google.gson.annotations.SerializedName

data class AppointmentRequest(
    @SerializedName("user_id") val userId: String?,
    @SerializedName("product_name") val productName: String,
    @SerializedName("estimated_price") val estimatedPrice: Double,
    @SerializedName("service_type") val serviceType: String,
    @SerializedName("appointment_date") val appointmentDate: String, // "YYYY-MM-DD"
    @SerializedName("appointment_time") val appointmentTime: String, // "HH:MM:SS"
    val address: String,
    val city: String,
    val notes: String
)

data class AppointmentResponse(
    val success: Boolean,
    val message: String?,
    val data: List<AppointmentData>?
)

data class AppointmentCreateResponse(
    val success: Boolean,
    val message: String,
    val data: List<AppointmentData>?
)

data class AppointmentData(
    val id: String,
    @SerializedName("user_id") val userId: String?,
    @SerializedName("product_name") val productName: String,
    @SerializedName("estimated_price") val estimatedPrice: Double,
    @SerializedName("service_type") val serviceType: String,
    @SerializedName("appointment_date") val appointmentDate: String,
    @SerializedName("appointment_time") val appointmentTime: String,
    val address: String,
    val city: String,
    val notes: String,
    val status: String,
    @SerializedName("created_at") val createdAt: String
)
