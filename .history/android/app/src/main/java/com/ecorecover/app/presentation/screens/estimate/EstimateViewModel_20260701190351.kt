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

    init {
        loadEstimate("Iphone")
    }

    fun loadEstimate(product: String) {

        viewModelScope.launch {

            _uiState.value = EstimateUiState(
                isLoading = true
            )

            try {

                val response =
                    repository.getEstimate(product)

                _uiState.value = EstimateUiState(
                    estimate = response.data
                )

            } catch (e: Exception) {

                _uiState.value = EstimateUiState(
                    error = e.localizedMessage ?: "Unknown error"
                )

            }

        }

    }

}