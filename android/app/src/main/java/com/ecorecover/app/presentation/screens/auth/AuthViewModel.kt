package com.ecorecover.app.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ecorecover.app.data.model.LoginRequest
import com.ecorecover.app.data.model.SignupRequest
import com.ecorecover.app.data.model.ResendRequest
import com.ecorecover.app.data.repository.AuthRepository
import com.ecorecover.app.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _signupState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signupState: StateFlow<AuthState> = _signupState

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _resendState = MutableStateFlow<AuthState>(AuthState.Idle)
    val resendState: StateFlow<AuthState> = _resendState

    fun signup(fullName: String, email: String, password: String) {
        if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
            _signupState.value = AuthState.Error("All fields are required")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _signupState.value = AuthState.Error("Invalid email address format")
            return
        }
        if (password.length < 6) {
            _signupState.value = AuthState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _signupState.value = AuthState.Loading
            try {
                val response = repository.signup(SignupRequest(fullName, email, password))
                if (response.success) {
                    _signupState.value = AuthState.Success(response.message)
                } else {
                    _signupState.value = AuthState.Error(response.message)
                }
            } catch (e: Exception) {
                _signupState.value = AuthState.Error(e.localizedMessage ?: "Connection error")
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = AuthState.Error("Email and password are required")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginState.value = AuthState.Error("Invalid email address format")
            return
        }

        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                val response = repository.login(LoginRequest(email, password))
                if (response.success && response.data != null) {
                    val loginData = response.data
                    sessionManager.saveSession(
                        token = loginData.access_token,
                        userId = loginData.user.id,
                        email = loginData.user.email,
                        fullName = loginData.user.fullName
                    )
                    _loginState.value = AuthState.Success(response.message)
                } else {
                    _loginState.value = AuthState.Error(response.message)
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.localizedMessage ?: "Connection error")
            }
        }
    }

    fun resendVerification(email: String) {
        if (email.isBlank()) {
            _resendState.value = AuthState.Error("Email is required")
            return
        }

        viewModelScope.launch {
            _resendState.value = AuthState.Loading
            try {
                val response = repository.resend(ResendRequest(email))
                if (response.success) {
                    _resendState.value = AuthState.Success(response.message)
                } else {
                    _resendState.value = AuthState.Error(response.message)
                }
            } catch (e: Exception) {
                _resendState.value = AuthState.Error(e.localizedMessage ?: "Connection error")
            }
        }
    }

    fun logout() {
        sessionManager.clearSession()
        _loginState.value = AuthState.Idle
        _signupState.value = AuthState.Idle
        _resendState.value = AuthState.Idle
    }

    fun resetStates() {
        _loginState.value = AuthState.Idle
        _signupState.value = AuthState.Idle
        _resendState.value = AuthState.Idle
    }

    class Factory(
        private val repository: AuthRepository,
        private val sessionManager: SessionManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(repository, sessionManager) as T
        }
    }
}
