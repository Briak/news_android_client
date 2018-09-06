package com.briak.newsclient.model.domain.allnews

import com.briak.newsclient.entities.news.server.Article

interface AllNewsInteractor {
    suspend fun getAllNews(query: String?, date: String?): List<Article>
}