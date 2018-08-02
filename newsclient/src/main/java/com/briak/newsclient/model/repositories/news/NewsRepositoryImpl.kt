package com.briak.newsclient.model.repositories.news

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.data.server.NewsApi
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
        private var newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNews(
            country: String,
            category: String
    ): Deferred<RSS> =
            newsApi.getHeadliners(country, category)
}
