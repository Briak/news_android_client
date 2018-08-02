package com.briak.newsclient.model.data.server

import com.briak.newsclient.entities.news.server.RSS
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    companion object {
        const val API_PATH = "/v2"
        const val API_KEY = "6e1ea05222d34fc8b296bf19cc57b12d"
    }

    @GET("$API_PATH/top-headlines")
    fun getHeadliners(
            @Query("country") country: String,
            @Query("category") category: String,
            @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<RSS>
}