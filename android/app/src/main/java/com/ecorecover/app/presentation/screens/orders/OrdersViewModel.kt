package com.ecorecover.app.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.AppointmentData
import com.ecorecover.app.data.repository.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface OrdersUiState {
    object Loading : OrdersUiState
    data class Success(val orders: List<AppointmentData>) : OrdersUiState
    object Empty : OrdersUiState
    data class Error(val message: String) : OrdersUiState
}

sealed interface OrderDetailUiState {
    object Loading : OrderDetailUiState
    data class Success(val order: AppointmentData) : OrderDetailUiState
    data class Error(val message: String) : OrderDetailUiState
}

class OrdersViewModel : ViewModel() {
    private val repository = AppointmentRepository()

    private val _uiState = MutableStateFlow<OrdersUiState>(OrdersUiState.Loading)
    val uiState: StateFlow<OrdersUiState> = _uiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<OrderDetailUiState>(OrderDetailUiState.Loading)
    val detailUiState: StateFlow<OrderDetailUiState> = _detailUiState.asStateFlow()

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            _uiState.value = OrdersUiState.Loading
            try {
                val response = repository.getOrders()
                if (response.success && response.data != null) {
                    if (response.data.isEmpty()) {
                        _uiState.value = OrdersUiState.Empty
                    } else {
                        _uiState.value = OrdersUiState.Success(response.data)
                    }
                } else {
                    _uiState.value = OrdersUiState.Error(response.message ?: "Failed to load orders")
                }
            } catch (e: Exception) {
                _uiState.value = OrdersUiState.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    fun loadOrderDetail(orderId: String) {
        viewModelScope.launch {
            _detailUiState.value = OrderDetailUiState.Loading
            try {
                val response = repository.getOrderDetail(orderId)
                if (response.success && response.data != null) {
                    _detailUiState.value = OrderDetailUiState.Success(response.data)
                } else {
                    _detailUiState.value = OrderDetailUiState.Error(response.message ?: "Failed to load order details")
                }
            } catch (e: Exception) {
                _detailUiState.value = OrderDetailUiState.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}
