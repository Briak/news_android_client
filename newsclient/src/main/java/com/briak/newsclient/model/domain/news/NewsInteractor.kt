package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.RSS
import kotlinx.coroutines.experimental.Deferred

interface NewsInteractor {
    suspend fun getTopNews(): RSS
    fun setCategory(category: String)
    fun getCategory(): String
    suspend fun doSomething(): Int
}