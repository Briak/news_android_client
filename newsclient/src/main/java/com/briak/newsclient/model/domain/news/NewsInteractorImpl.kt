package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.repositories.news.NewsRepository
import javax.inject.Inject

class NewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository
) : NewsInteractor {
    override suspend fun getTopNews(): RSS =
            repository.getNews("us", "business").await()

    override suspend fun getAllNews(): RSS =
            repository.getNews("ru", "business").await()

}