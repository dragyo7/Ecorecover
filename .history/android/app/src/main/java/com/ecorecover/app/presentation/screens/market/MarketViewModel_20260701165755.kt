package com.ecorecover.app.presentation.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.MetalPriceResponse
import com.ecorecover.app.data.repository.MarketRepository
import com.ecorecover.app.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel : ViewModel() {

    private val repository = MarketRepository()

    private val _uiState =
        MutableStateFlow<UiState<List<MetalPriceResponse>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<MetalPriceResponse>>> =
        _uiState

    init {
        loadPrices()
    }

    fun loadPrices() {

        viewModelScope.launch {

            _uiState.value = UiState.Loading

            try {

                _uiState.value =
                    UiState.Success(
                        repository.getPrices()
                    )

            } catch (e: Exception) {

                _uiState.value =
                    UiState.Error(
                        e.localizedMessage ?: "Unknown Error"
                    )

            }

        }

    }

}