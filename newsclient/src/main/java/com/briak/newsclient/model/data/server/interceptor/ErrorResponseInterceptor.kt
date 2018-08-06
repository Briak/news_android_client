package com.briak.newsclient.model.data.server.interceptor

import com.briak.newsclient.entities.news.server.ErrorResponse
import com.briak.newsclient.model.data.server.ServerError
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

class ErrorResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val code = response.code()
        if (code == 400 || code == 401 || code == 429 || code == 500) {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter<ErrorResponse>(ErrorResponse::class.java)
            val errorResponse = jsonAdapter.fromJson(String(response.body()!!.source().readByteArray(), Charset.defaultCharset()))
            throw ServerError(errorResponse)
        }

        return response
    }
}