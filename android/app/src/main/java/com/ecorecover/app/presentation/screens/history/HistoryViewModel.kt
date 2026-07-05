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

    init {
        Log.d("HistoryTrace", "HistoryViewModel created")
        Log.d("HistoryTrace", "HistoryViewModel created")
        loadHistory()
    }

    fun loadHistory() {
        Log.d("HistoryTrace", "loadHistory() invoked")
        Log.d("HistoryTrace", "loadHistory() invoked")
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading
            Log.d("HistoryTrace", "Set UI state to Loading")
            try {
                Log.d("HistoryTrace", "Calling repository.getAppointments()")
                val response = withContext(Dispatchers.IO) { appointmentRepository.getAppointments() }
                Log.d("HistoryTrace", "Repository response success=${response.success}, dataSize=${response.data?.size}")
                if (response.success && response.data != null) {
                    val list = response.data
                    Log.d("HistoryTrace", "Response data size=${list.size}")
                    if (list.isEmpty()) {
                        _uiState.value = HistoryUiState.Empty
                Log.d("HistoryTrace", "Emitted Empty state")
                        Log.d("HistoryTrace", "Emitted Empty state")
                    } else {
                        _uiState.value = HistoryUiState.Success(list)
                Log.d("HistoryTrace", "Emitted Success state with ${list.size} appointments")
                        Log.d("HistoryTrace", "Emitted Success state with ${list.size} appointments")
                    }
                } else {
                    val msg = response.message ?: "Failed to load history"
                Log.d("HistoryTrace", "Emitted Error state: $msg")
                    _uiState.value = HistoryUiState.Error(msg)
                    Log.d("HistoryTrace", "Emitted Error state: $msg")
                }
            } catch (e: Exception) {
                val err = e.localizedMessage ?: "Unknown error"
                _uiState.value = HistoryUiState.Error(err)
                Log.d("HistoryTrace", "Exception caught: $err")
            }
        }
    }
}
