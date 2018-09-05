package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.Article

interface NewsInteractor {
    suspend fun getTopNews(query: String?): List<Article>
    fun setCategory(category: String)
    fun getCategory(): String
}