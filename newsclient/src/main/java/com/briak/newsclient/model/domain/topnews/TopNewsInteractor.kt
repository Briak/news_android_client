package com.briak.newsclient.model.domain.topnews

import com.briak.newsclient.entities.news.server.RSS
import io.reactivex.Single

interface TopNewsInteractor {
    fun getTopNews(): Single<RSS>
    fun setCategory(category: String)
    fun getCategory(): String
}