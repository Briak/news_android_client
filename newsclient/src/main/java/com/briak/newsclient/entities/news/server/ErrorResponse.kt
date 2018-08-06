package com.briak.newsclient.entities.news.server

data class ErrorResponse(
        val status: String,
        val code: String,
        val message: String
)