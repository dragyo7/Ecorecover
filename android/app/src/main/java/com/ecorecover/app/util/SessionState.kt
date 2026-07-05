package com.ecorecover.app.util

sealed class SessionState {
    data class Authenticated(val userId: String, val userName: String) : SessionState()
    object Unauthenticated : SessionState()
}
