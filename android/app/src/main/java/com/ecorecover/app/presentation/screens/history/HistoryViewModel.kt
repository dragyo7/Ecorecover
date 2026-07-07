package com.ecorecover.app.presentation.screens.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.AppointmentData
import com.ecorecover.app.data.model.AppointmentResponse
import com.ecorecover.app.data.repository.AppointmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// UI states for History screen
sealed interface HistoryUiState {
    object Loading : HistoryUiState
    data class Success(val appointments: List<AppointmentData>) : HistoryUiState
    object Empty : HistoryUiState
    data class Error(val message: String) : HistoryUiState
}

class HistoryViewModel : ViewModel() {
    private val appointmentRepository = AppointmentRepository()
    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        Log.d("HistoryTrace", "HistoryViewModel created")
        loadHistory()
    }

    fun loadHistory(isRefresh: Boolean = false) {
        Log.d("HistoryTrace", "loadHistory() invoked, isRefresh=$isRefresh")
        viewModelScope.launch {
            if (!isRefresh) {
                _uiState.value = HistoryUiState.Loading
            } else {
                _isRefreshing.value = true
            }
            try {
                Log.d("HistoryTrace", "Calling repository.getAppointments()")
                val response = withContext(Dispatchers.IO) { appointmentRepository.getAppointments() }
                Log.d("HistoryTrace", "Repository response success=${response.success}, dataSize=${response.data?.size}")
                if (response.success && response.data != null) {
                    val list = response.data
                    if (list.isEmpty()) {
                        _uiState.value = HistoryUiState.Empty
                    } else {
                        _uiState.value = HistoryUiState.Success(list)
                    }
                } else {
                    val msg = response.message ?: "Failed to load history"
                    _uiState.value = HistoryUiState.Error(msg)
                }
            } catch (e: Exception) {
                val err = e.localizedMessage ?: "Unknown error"
                _uiState.value = HistoryUiState.Error(err)
            } finally {
                if (isRefresh) {
                    _isRefreshing.value = false
                }
            }
        }
    }
}
