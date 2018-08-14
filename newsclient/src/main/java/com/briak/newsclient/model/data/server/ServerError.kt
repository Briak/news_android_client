package com.briak.newsclient.model.data.server

import com.briak.newsclient.entities.news.server.ErrorResponse
import java.io.IOException

class ServerError(val errorResponse: ErrorResponse): IOException()