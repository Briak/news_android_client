package com.briak.newsclient.model.domain.allnews

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.di.allnews.AllNewsScope
import com.briak.newsclient.model.repositories.news.NewsRepository
import io.reactivex.Single
import javax.inject.Inject

@AllNewsScope
class AllNewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository
) : AllNewsInteractor {

    override fun getAllNews(query: String?, date: String?): Single<RSS> =
            repository.getAllNews(query, date, date)
}