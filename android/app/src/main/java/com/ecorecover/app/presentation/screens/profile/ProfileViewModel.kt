package com.ecorecover.app.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.ProfileData
import com.ecorecover.app.data.model.ProfileUpdateRequest
import com.ecorecover.app.data.repository.AuthRepository
import com.ecorecover.app.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProfileUiState {
    object Loading : ProfileUiState
    data class Success(val profile: ProfileData) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

class ProfileViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating.asStateFlow()

    private val _updateMessage = MutableStateFlow<String?>(null)
    val updateMessage: StateFlow<String?> = _updateMessage.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!isRefresh) {
                _uiState.value = ProfileUiState.Loading
            } else {
                _isRefreshing.value = true
            }
            try {
                val response = repository.getProfile()
                if (response.success && response.data != null) {
                    _uiState.value = ProfileUiState.Success(response.data)
                } else {
                    _uiState.value = ProfileUiState.Error(response.message ?: "Failed to load profile details")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.localizedMessage ?: "Unknown error occurred")
            } finally {
                if (isRefresh) {
                    _isRefreshing.value = false
                }
            }
        }
    }

    fun updateProfileName(newName: String, sessionManager: SessionManager) {
        viewModelScope.launch {
            _isUpdating.value = true
            _updateMessage.value = null
            try {
                val request = ProfileUpdateRequest(fullName = newName)
                val response = repository.updateProfile(request)
                if (response.success && response.data != null) {
                    // Update locally stored full name in SessionManager
                    sessionManager.saveFullName(response.data.fullName)
                    _uiState.value = ProfileUiState.Success(response.data)
                    _updateMessage.value = "Name updated successfully!"
                } else {
                    _updateMessage.value = response.message ?: "Failed to update name"
                }
            } catch (e: Exception) {
                _updateMessage.value = e.localizedMessage ?: "Unknown error occurred"
            } finally {
                _isUpdating.value = false
            }
        }
    }

    fun clearUpdateMessage() {
        _updateMessage.value = null
    }
}
