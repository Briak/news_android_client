package com.briak.newsclient.model.repositories.news

import com.briak.newsclient.entities.news.server.RSS
import kotlinx.coroutines.experimental.Deferred

interface NewsRepository {

    fun getNews(
            country: String
    ): Deferred<RSS>
}