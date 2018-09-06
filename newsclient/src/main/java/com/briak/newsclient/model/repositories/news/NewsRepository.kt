package com.briak.newsclient.model.repositories.news

import com.briak.newsclient.entities.news.server.RSS
import kotlinx.coroutines.experimental.Deferred

interface NewsRepository {
    fun getAllNews(
            query: String?,
            fromDate: String?,
            toDate: String?
    ): Deferred<RSS>

    fun getTopNews(country: String) : Deferred<RSS>
}