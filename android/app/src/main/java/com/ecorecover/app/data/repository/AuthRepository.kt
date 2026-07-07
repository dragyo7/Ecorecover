package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.LoginRequest
import com.ecorecover.app.data.model.LoginResponse
import com.ecorecover.app.data.model.SignupRequest
import com.ecorecover.app.data.model.SignupResponse
import com.ecorecover.app.data.model.ResendRequest
import com.ecorecover.app.data.model.ResendResponse
import com.ecorecover.app.data.model.ProfileResponse
import com.ecorecover.app.data.model.ProfileUpdateRequest
import com.ecorecover.app.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.ecorecover.app.data.remote.safeApiCall

class AuthRepository {

    suspend fun signup(request: SignupRequest): SignupResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.signup(request) }
    }

    suspend fun login(request: LoginRequest): LoginResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.login(request) }
    }

    suspend fun resend(request: ResendRequest): ResendResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.resend(request) }
    }

    suspend fun getProfile(): ProfileResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.getProfile() }
    }

    suspend fun updateProfile(request: ProfileUpdateRequest): ProfileResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.updateProfile(request) }
    }
}
