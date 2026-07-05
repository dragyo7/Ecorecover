package com.ecorecover.app.presentation.screens.estimate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.repository.EstimateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstimateViewModel : ViewModel() {

    private val repository = EstimateRepository()

    private val _uiState =
        MutableStateFlow(EstimateUiState())

    val uiState: StateFlow<EstimateUiState> = _uiState

    fun loadEstimate(product: String) {

        viewModelScope.launch {

            _uiState.value = EstimateUiState(
                isLoading = true
            )

            try {

                val response =
                    repository.getEstimate(product)

                if (response.success && response.data != null) {
                    _uiState.value = EstimateUiState(
                        estimate = response.data
                    )
                } else {
                    _uiState.value = EstimateUiState(
                        error = response.message.ifBlank { "Failed to get device valuation estimate" }
                    )
                }

            } catch (e: Exception) {

                _uiState.value = EstimateUiState(
                    error = e.localizedMessage ?: "Unknown error"
                )

            }

        }

    }

}