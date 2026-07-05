package com.ecorecover.app.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.repository.MarketRepository
import com.ecorecover.app.data.repository.AppointmentRepository
import com.ecorecover.app.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class HomeViewModel(
    private val marketRepository: MarketRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val appointmentRepository = AppointmentRepository()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        Log.d("HomeViewModel", "init block called")
        loadHomeData()
    }

    fun loadHomeData() {
        Log.d("HomeViewModel", "loadHomeData() invoked")
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            Log.d("HomeViewModel", "Set UI state to Loading")
            fetchData()
        }
    }

    fun refreshHomeData() {
        Log.d("HomeViewModel", "refreshHomeData() invoked")
        viewModelScope.launch {
            _isRefreshing.value = true
            Log.d("HomeViewModel", "Refresh indicator set to true")
            fetchData()
            _isRefreshing.value = false
            Log.d("HomeViewModel", "Refresh indicator set to false")
        }
    }

    private suspend fun fetchData() {
        Log.d("HomeViewModel", "fetchData() start")
        withContext(Dispatchers.IO) {
            try {
                val userName = sessionManager.getFullName() ?: "EcoUser"
                Log.d("HomeViewModel", "Obtained userName: $userName")
                val priceMap = marketRepository.getPrices()
                Log.d("HomeViewModel", "Fetched prices map, size=${priceMap.size}")
                val appointmentsResponse = try {
                    appointmentRepository.getAppointments()
                } catch (e: Exception) {
                    Log.d("HomeViewModel", "Failed to fetch appointments: ${e.localizedMessage}")
                    null
                }
                val appointments = if (appointmentsResponse?.success == true) {
                    appointmentsResponse.data ?: emptyList()
                } else {
                    emptyList()
                }
                Log.d("HomeViewModel", "Fetched appointments, size=${appointments.size}")
                _uiState.value = HomeUiState.Success(userName, priceMap, appointments)
                Log.d("HomeViewModel", "Set UI state to Success")
            } catch (e: Exception) {
                val msg = e.localizedMessage ?: "Failed to load live data"
                _uiState.value = HomeUiState.Error(msg)
                Log.d("HomeViewModel", "Set UI state to Error: $msg")
            }
        }
    }

    class Factory(
        private val marketRepository: MarketRepository,
        private val sessionManager: SessionManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(marketRepository, sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
