package com.briak.newsclient.model.repositories.news

import com.briak.newsclient.entities.news.server.RSS
import kotlinx.coroutines.experimental.Deferred

interface NewsRepository {
    suspend fun getNews(
            country: String,
            category: String
    ): Deferred<RSS>
}