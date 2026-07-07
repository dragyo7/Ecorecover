package com.ecorecover.app.data.model

import com.google.gson.annotations.SerializedName

data class PaymentOrderRequest(
    @SerializedName("appointment_id") val appointmentId: String,
    val amount: Double
)

data class PaymentOrderResponse(
    val success: Boolean,
    @SerializedName("order_id") val orderId: String,
    val amount: Double,
    val currency: String
)

data class PaymentVerifyRequest(
    @SerializedName("appointment_id") val appointmentId: String,
    @SerializedName("razorpay_order_id") val razorpayOrderId: String,
    @SerializedName("razorpay_payment_id") val razorpayPaymentId: String,
    @SerializedName("razorpay_signature") val razorpaySignature: String,
    val amount: Double
)

data class PaymentVerifyResponse(
    val success: Boolean,
    val message: String,
    val data: TransactionData?
)

data class TransactionData(
    val id: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("appointment_id") val appointmentId: String,
    @SerializedName("payment_id") val paymentId: String,
    @SerializedName("order_id") val orderId: String,
    val amount: Double,
    val status: String,
    @SerializedName("receipt_id") val receiptId: String,
    @SerializedName("created_at") val createdAt: String
)

data class TransactionResponse(
    val success: Boolean,
    val data: List<TransactionData>
)
