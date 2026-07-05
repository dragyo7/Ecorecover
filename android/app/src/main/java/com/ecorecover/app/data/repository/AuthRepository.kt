package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.LoginRequest
import com.ecorecover.app.data.model.LoginResponse
import com.ecorecover.app.data.model.SignupRequest
import com.ecorecover.app.data.model.SignupResponse
import com.ecorecover.app.data.model.ResendRequest
import com.ecorecover.app.data.model.ResendResponse
import com.ecorecover.app.data.remote.RetrofitClient

class AuthRepository {

    suspend fun signup(request: SignupRequest): SignupResponse {
        return RetrofitClient.api.signup(request)
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitClient.api.login(request)
    }

    suspend fun resend(request: ResendRequest): ResendResponse {
        return RetrofitClient.api.resend(request)
    }
}
