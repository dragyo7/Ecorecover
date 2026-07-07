package com.ecorecover.app.presentation.screens.rewards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.RewardsData
import com.ecorecover.app.data.repository.RewardsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RewardsUiState {
    object Loading : RewardsUiState
    data class Success(val data: RewardsData) : RewardsUiState
    data class Error(val message: String) : RewardsUiState
}

class RewardsViewModel : ViewModel() {
    private val repository = RewardsRepository()

    private val _uiState = MutableStateFlow<RewardsUiState>(RewardsUiState.Loading)
    val uiState: StateFlow<RewardsUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadRewards()
    }

    fun loadRewards(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!isRefresh) {
                _uiState.value = RewardsUiState.Loading
            } else {
                _isRefreshing.value = true
            }
            try {
                val response = repository.getRewards()
                if (response.success && response.data != null) {
                    _uiState.value = RewardsUiState.Success(response.data)
                } else {
                    _uiState.value = RewardsUiState.Error("Failed to fetch rewards details")
                }
            } catch (e: Exception) {
                _uiState.value = RewardsUiState.Error(e.localizedMessage ?: "Unknown error occurred")
            } finally {
                if (isRefresh) {
                    _isRefreshing.value = false
                }
            }
        }
    }
}
