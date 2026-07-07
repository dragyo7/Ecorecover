package com.ecorecover.app.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.PaymentVerifyRequest
import com.ecorecover.app.data.model.TransactionData
import com.ecorecover.app.data.repository.PaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PaymentUiState {
    object Idle : PaymentUiState
    object Loading : PaymentUiState
    data class OrderInitialized(val orderId: String, val amount: Double) : PaymentUiState
    data class Success(val transaction: TransactionData) : PaymentUiState
    data class Error(val message: String) : PaymentUiState
}

class PaymentViewModel : ViewModel() {
    private val repository = PaymentRepository()

    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Idle)
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun createPaymentOrder(appointmentId: String, amount: Double) {
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Loading
            try {
                val orderRes = repository.createPaymentOrder(appointmentId, amount)
                if (orderRes.success) {
                    _uiState.value = PaymentUiState.OrderInitialized(orderRes.orderId, amount)
                } else {
                    _uiState.value = PaymentUiState.Error("Failed to initialize Razorpay checkout session.")
                }
            } catch (e: Exception) {
                _uiState.value = PaymentUiState.Error(e.localizedMessage ?: "Network connection failure.")
            }
        }
    }

    fun verifyPayment(appointmentId: String, orderId: String, paymentId: String, amount: Double) {
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Loading
            try {
                val verifyRequest = PaymentVerifyRequest(
                    appointmentId = appointmentId,
                    razorpayOrderId = orderId,
                    razorpayPaymentId = paymentId,
                    razorpaySignature = "sig_rp_" + java.util.UUID.randomUUID().toString().replace("-", "").take(16),
                    amount = amount
                )
                val verifyRes = repository.verifyPayment(verifyRequest)
                if (verifyRes.success && verifyRes.data != null) {
                    _uiState.value = PaymentUiState.Success(verifyRes.data)
                } else {
                    _uiState.value = PaymentUiState.Error(verifyRes.message)
                }
            } catch (e: Exception) {
                _uiState.value = PaymentUiState.Error(e.localizedMessage ?: "Verification network failure.")
            }
        }
    }

    fun setPaymentError(message: String) {
        _uiState.value = PaymentUiState.Error(message)
    }

    fun resetState() {
        _uiState.value = PaymentUiState.Idle
    }
}
