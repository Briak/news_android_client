package com.briak.newsclient.model.repositories.news

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.data.server.NewsApi
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
        private var newsApi: NewsApi,
        private var newsHolder: CategoriesHolder
) : NewsRepository {

    override fun getAllNews(
            query: String?,
            fromDate: String?,
            toDate: String?
    ): Deferred<RSS> =
            newsApi.getEverything(
                    query,
                    fromDate,
                    toDate
            )

    override fun getTopNews(country: String): Deferred<RSS> =
            newsApi.getHeadliners(country, newsHolder.category)
}
