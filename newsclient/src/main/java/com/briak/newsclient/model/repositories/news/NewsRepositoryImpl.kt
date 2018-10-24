package com.briak.newsclient.model.repositories.news

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.data.server.NewsApi
import com.briak.newsclient.model.system.SchedulersProvider
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
        private var newsApi: NewsApi,
        private val schedulers: SchedulersProvider,
        private var newsHolder: CategoriesHolder
) : NewsRepository {

    override fun getAllNews(query: String?, fromDate: String?, toDate: String?): Single<RSS> =
            newsApi
                    .getEverything(query, fromDate, toDate)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())


    override fun getTopNews(country: String): Single<RSS> =
            newsApi
                    .getHeadliners(country, newsHolder.category)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
}
