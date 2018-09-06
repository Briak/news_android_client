package com.briak.newsclient.model.domain.allnews

import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.di.allnews.AllNewsScope
import com.briak.newsclient.model.repositories.news.NewsRepository
import javax.inject.Inject

@AllNewsScope
class AllNewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository
) : AllNewsInteractor {

    override suspend fun getAllNews(query: String?, date: String?): List<Article> =
            repository.getAllNews(query, date, date).await().articles
}