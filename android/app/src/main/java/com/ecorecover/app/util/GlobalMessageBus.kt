package com.ecorecover.app.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object GlobalMessageBus {
    private val _errors = MutableSharedFlow<String>(extraBufferCapacity = 64)
    val errors: SharedFlow<String> = _errors.asSharedFlow()

    private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 64)
    val messages: SharedFlow<String> = _messages.asSharedFlow()

    fun sendError(message: String) {
        _errors.tryEmit(message)
    }

    fun sendMessage(message: String) {
        _messages.tryEmit(message)
    }
}
