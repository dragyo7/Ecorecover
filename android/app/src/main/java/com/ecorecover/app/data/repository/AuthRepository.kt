package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.LoginRequest
import com.ecorecover.app.data.model.LoginResponse
import com.ecorecover.app.data.model.SignupRequest
import com.ecorecover.app.data.model.SignupResponse
import com.ecorecover.app.data.model.ResendRequest
import com.ecorecover.app.data.model.ResendResponse
import com.ecorecover.app.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {

    suspend fun signup(request: SignupRequest): SignupResponse = withContext(Dispatchers.IO) {
        RetrofitClient.api.signup(request)
    }

    suspend fun login(request: LoginRequest): LoginResponse = withContext(Dispatchers.IO) {
        RetrofitClient.api.login(request)
    }

    suspend fun resend(request: ResendRequest): ResendResponse = withContext(Dispatchers.IO) {
        RetrofitClient.api.resend(request)
    }
}
