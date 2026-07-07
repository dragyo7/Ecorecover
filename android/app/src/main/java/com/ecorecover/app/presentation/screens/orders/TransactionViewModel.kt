package com.ecorecover.app.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.TransactionData
import com.ecorecover.app.data.repository.PaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface TransactionUiState {
    object Loading : TransactionUiState
    data class Success(val list: List<TransactionData>) : TransactionUiState
    data class Error(val message: String) : TransactionUiState
}

class TransactionViewModel : ViewModel() {
    private val repository = PaymentRepository()

    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = TransactionUiState.Loading
            try {
                val res = repository.getTransactions()
                if (res.success) {
                    _uiState.value = TransactionUiState.Success(res.data)
                } else {
                    _uiState.value = TransactionUiState.Error("Failed to load transaction history")
                }
            } catch (e: Exception) {
                _uiState.value = TransactionUiState.Error(e.localizedMessage ?: "Unknown network failure")
            }
        }
    }
}
