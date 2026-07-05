package com.ecorecover.app.data.model

data class SignupResponse(
    val success: Boolean,
    val message: String,
    val status: String? = null,
    val data: Map<String, Any>? = null
)
