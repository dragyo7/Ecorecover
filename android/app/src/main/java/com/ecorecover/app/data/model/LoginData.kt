package com.ecorecover.app.data.model

data class LoginData(
    val user: UserInfo,
    val access_token: String
)

data class UserInfo(
    val id: String,
    val email: String,
    val user_metadata: Map<String, Any>? = null
) {
    val fullName: String
        get() = (user_metadata?.get("full_name") as? String) ?: ""
}
