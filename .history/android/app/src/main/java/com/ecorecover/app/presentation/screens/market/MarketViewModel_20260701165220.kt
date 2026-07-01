package com.ecorecover.app.presentation.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.MetalPriceResponse
import com.ecorecover.app.data.repository.MarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel : ViewModel() {

    private val repository = MarketRepository()

    private val _prices = MutableStateFlow<List<MetalPriceResponse>>(emptyList())
    val prices: StateFlow<List<MetalPriceResponse>> = _prices

    init {
        loadPrices()
    }

    fun loadPrices() {

        viewModelScope.launch {

            try {

                _prices.value = repository.getPrices()

            } catch (e: Exception) {

                e.printStackTrace()

            }

        }

    }

}