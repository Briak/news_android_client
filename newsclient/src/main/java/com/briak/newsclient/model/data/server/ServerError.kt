package com.briak.newsclient.model.data.server

import com.briak.newsclient.entities.news.server.ErrorResponse

class ServerError(val errorResponse: ErrorResponse): RuntimeException()