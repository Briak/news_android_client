package com.briak.newsclient.model.domain.topnews

import com.briak.newsclient.entities.news.server.Article

interface TopNewsInteractor {
    suspend fun getTopNews(): List<Article>
    fun setCategory(category: String)
    fun getCategory(): String
}