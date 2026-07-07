package com.ecorecover.app.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.PaymentVerifyRequest
import com.ecorecover.app.data.model.TransactionData
import com.ecorecover.app.data.repository.PaymentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PaymentUiState {
    object Idle : PaymentUiState
    object Loading : PaymentUiState
    data class Success(val transaction: TransactionData) : PaymentUiState
    data class Error(val message: String) : PaymentUiState
}

class PaymentViewModel : ViewModel() {
    private val repository = PaymentRepository()

    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Idle)
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun processPayment(appointmentId: String, amount: Double) {
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Loading
            try {
                // 1. Create simulated Razorpay Order
                val orderRes = repository.createPaymentOrder(appointmentId, amount)
                if (orderRes.success) {
                    // Simulate processing time (equivalent to SDK prompt)
                    delay(1500)
                    
                    // 2. Call backend verification to release funds and update status
                    val verifyRequest = PaymentVerifyRequest(
                        appointmentId = appointmentId,
                        razorpayOrderId = orderRes.orderId,
                        razorpayPaymentId = "pay_rp_" + java.util.UUID.randomUUID().toString().replace("-", "").take(12),
                        razorpaySignature = "sig_rp_" + java.util.UUID.randomUUID().toString().replace("-", "").take(16),
                        amount = amount
                    )
                    
                    val verifyRes = repository.verifyPayment(verifyRequest)
                    if (verifyRes.success && verifyRes.data != null) {
                        _uiState.value = PaymentUiState.Success(verifyRes.data)
                    } else {
                        _uiState.value = PaymentUiState.Error(verifyRes.message)
                    }
                } else {
                    _uiState.value = PaymentUiState.Error("Failed to initialize Razorpay checkout session.")
                }
            } catch (e: Exception) {
                _uiState.value = PaymentUiState.Error(e.localizedMessage ?: "Network connection failure.")
            }
        }
    }

    fun resetState() {
        _uiState.value = PaymentUiState.Idle
    }
}
