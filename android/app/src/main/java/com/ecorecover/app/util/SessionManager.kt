package com.ecorecover.app.util

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    // SessionState flow
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Unauthenticated)
    val sessionState: StateFlow<SessionState> = _sessionState

    init {
        // Restore persisted session on init
        val token = prefs.getString(KEY_TOKEN, null)
        val userId = prefs.getString(KEY_USER_ID, null)
        val fullName = prefs.getString(KEY_NAME, null)
        if (!token.isNullOrBlank() && !userId.isNullOrBlank() && !fullName.isNullOrBlank()) {
            _sessionState.value = SessionState.Authenticated(userId, fullName)
        }
        // static reference for interceptor
        instance = this
    }

    fun saveSession(token: String, userId: String, email: String, fullName: String) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putString(KEY_USER_ID, userId)
            putString(KEY_EMAIL, email)
            putString(KEY_NAME, fullName)
            apply()
        }
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    fun getEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    fun getFullName(): String? {
        return prefs.getString(KEY_NAME, null)
    }

    fun saveFullName(fullName: String) {
        prefs.edit().putString(KEY_NAME, fullName).apply()
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun clearSession() {
        prefs.edit().clear().apply()
        _sessionState.value = SessionState.Unauthenticated
    }

    private val _isDarkMode = MutableStateFlow(prefs.getBoolean(KEY_DARK_MODE, false))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        _isDarkMode.value = enabled
    }

    companion object {
        private const val PREFS_NAME = "ecorecover_session"
        private const val KEY_TOKEN = "access_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "full_name"
        private const val KEY_DARK_MODE = "dark_mode"
        // static reference for interceptor
        var instance: SessionManager? = null
    }
}
