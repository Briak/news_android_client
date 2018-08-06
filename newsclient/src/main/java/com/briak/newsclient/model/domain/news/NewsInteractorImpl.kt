package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.repositories.news.NewsRepository
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

class NewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository
) : NewsInteractor {
    override fun getTopNews(): Deferred<RSS> =
            repository.getNews("us", "business")

    override fun getAllNews(): Deferred<RSS> =
            repository.getNews("ru", "business")

}