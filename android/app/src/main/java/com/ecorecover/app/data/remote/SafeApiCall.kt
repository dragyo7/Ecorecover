package com.ecorecover.app.data.remote

import android.util.Log
import com.ecorecover.app.util.GlobalMessageBus
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
    try {
        return apiCall()
    } catch (e: SocketTimeoutException) {
        val msg = "Connection timed out. Please check your internet connection."
        Log.e("NetworkResilience", msg, e)
        GlobalMessageBus.sendError(msg)
        throw IOException(msg, e)
    } catch (e: ConnectException) {
        val msg = "Unable to connect to the server. Please check if the server is running."
        Log.e("NetworkResilience", msg, e)
        GlobalMessageBus.sendError(msg)
        throw IOException(msg, e)
    } catch (e: UnknownHostException) {
        val msg = "Server host not found. Please check your network connection."
        Log.e("NetworkResilience", msg, e)
        GlobalMessageBus.sendError(msg)
        throw IOException(msg, e)
    } catch (e: IOException) {
        val msg = "Network error: ${e.localizedMessage ?: "Please try again later."}"
        Log.e("NetworkResilience", msg, e)
        GlobalMessageBus.sendError(msg)
        throw IOException(msg, e)
    } catch (e: Exception) {
        val msg = "Unexpected error: ${e.localizedMessage ?: "Please try again later."}"
        Log.e("NetworkResilience", msg, e)
        GlobalMessageBus.sendError(msg)
        throw Exception(msg, e)
    }
}
