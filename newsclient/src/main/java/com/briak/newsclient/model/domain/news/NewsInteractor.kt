package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.RSS
import kotlinx.coroutines.experimental.Deferred

interface NewsInteractor {
    fun getTopNews(): Deferred<RSS>
    fun getAllNews(): Deferred<RSS>
}