package com.ecorecover.app.data.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val status: String? = null,
    val data: LoginData? = null
)
