package com.ecorecover.app.presentation.screens.estimate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.ProductEstimateResponse
import com.ecorecover.app.data.repository.EstimateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstimateViewModel : ViewModel() {

    private val repository = EstimateRepository()

    private val _estimate = MutableStateFlow<ProductEstimateResponse?>(null)
    val estimate: StateFlow<ProductEstimateResponse?> = _estimate

    fun loadEstimate(productName: String) {

        viewModelScope.launch {

            try {

                _estimate.value = repository.getEstimate(productName)

            } catch (e: Exception) {

                e.printStackTrace()

            }

        }

    }

}