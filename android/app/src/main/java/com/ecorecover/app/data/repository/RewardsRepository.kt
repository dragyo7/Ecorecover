package com.ecorecover.app.data.repository

import com.ecorecover.app.data.model.RewardsResponse
import com.ecorecover.app.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.ecorecover.app.data.remote.safeApiCall

class RewardsRepository {
    suspend fun getRewards(): RewardsResponse = withContext(Dispatchers.IO) {
        safeApiCall { RetrofitClient.api.getRewards() }
    }
}
