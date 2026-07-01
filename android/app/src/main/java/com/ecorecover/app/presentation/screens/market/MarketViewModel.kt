package com.ecorecover.app.presentation.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.MetalPrice
import com.ecorecover.app.data.repository.MarketRepository
import com.ecorecover.app.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel : ViewModel() {

    private val repository = MarketRepository()

    private val _prices =
        MutableStateFlow<UiState<Map<String, MetalPrice>>>(UiState.Loading)

    val prices: StateFlow<UiState<Map<String, MetalPrice>>> = _prices

    init {
        loadPrices()
    }

    fun loadPrices() {

        viewModelScope.launch {

            try {

                val response = repository.getPrices()

                _prices.value = UiState.Success(response)

            } catch (e: Exception) {

                _prices.value =
                    UiState.Error(e.message ?: "Unknown Error")

            }

        }

    }

}