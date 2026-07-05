package com.ecorecover.app.data.remote

import com.ecorecover.app.util.SessionManager

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Global OkHttp interceptor that injects the JWT Authorization header when a token is present.
 * It also handles HTTP 401 responses by clearing the session exactly once.
 *
 * This interceptor never performs navigation – UI navigation is handled by observing SessionState
 * in the composable layer (EcoRecoverApp).
 */
class AuthInterceptor : Interceptor {
    // Guarantees that clearSession is invoked only once for concurrent 401 responses.
    private val logoutInProgress = AtomicBoolean(false)

    override fun intercept(chain: Interceptor.Chain): Response {
        android.util.Log.d("HistoryTrace", "AuthInterceptor entered")
        // Obtain the singleton SessionManager if it has been initialized.
        val sessionManager = SessionManager.instance
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // Attach Authorization header only when a non‑empty token exists.
        val token = sessionManager?.getToken()
        android.util.Log.d("HistoryTrace", "Token retrieved: ${if (token.isNullOrBlank()) "<empty>" else token}")
        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())

        // Centralised 401 handling – clear the session exactly once.
        if (response.code == 401) {
            if (logoutInProgress.compareAndSet(false, true)) {
                sessionManager?.clearSession()
            }
        }

        // Reset the flag so future 401s can be processed.
        if (logoutInProgress.get()) {
            logoutInProgress.set(false)
        }

        return response
    }
}
