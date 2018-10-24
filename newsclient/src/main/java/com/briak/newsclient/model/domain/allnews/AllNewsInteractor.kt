package com.briak.newsclient.model.domain.allnews

import com.briak.newsclient.entities.news.server.RSS
import io.reactivex.Single

interface AllNewsInteractor {
    fun getAllNews(query: String?, date: String?): Single<RSS>
}