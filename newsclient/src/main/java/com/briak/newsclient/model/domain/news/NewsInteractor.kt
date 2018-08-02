package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.RSS

interface NewsInteractor {
    suspend fun getTopNews(): RSS
    suspend fun getAllNews(): RSS
}