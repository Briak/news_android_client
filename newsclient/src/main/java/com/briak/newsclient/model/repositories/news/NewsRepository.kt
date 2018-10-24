package com.briak.newsclient.model.repositories.news

import com.briak.newsclient.entities.news.server.RSS
import io.reactivex.Single

interface NewsRepository {
    fun getAllNews(
            query: String?,
            fromDate: String?,
            toDate: String?
    ): Single<RSS>

    fun getTopNews(country: String) : Single<RSS>
}