package com.briak.newsclient.presentation.base

import com.briak.newsclient.extensions.userMessage
import com.briak.newsclient.model.data.server.ServerError
import com.briak.newsclient.model.system.ResourceManager
import javax.inject.Inject

class ErrorHandler @Inject constructor(private val resourceManager: ResourceManager) {
    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}) {
        if (error is ServerError) {
            messageListener(error.errorResponse.message)
        } else {
            messageListener(error.userMessage(resourceManager))
        }
    }
}