package com.ecorecover.app.data.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    val success: Boolean,
    val message: String? = null,
    val data: ProfileData?
)

data class ProfileData(
    val id: String,
    @SerializedName("full_name") val fullName: String,
    val email: String
)

data class ProfileUpdateRequest(
    @SerializedName("full_name") val fullName: String
)
