package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.repositories.news.NewsRepository
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

class NewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository,
        private var categoriesHolder: CategoriesHolder
) : NewsInteractor {

    override fun getTopNews(): Deferred<RSS> =
            repository.getNews("us")

    override fun getAllNews(): Deferred<RSS> =
            repository.getNews("ru")

    override fun setCategory(category: String) {
        categoriesHolder.category = category
    }

    override fun getCategory(): String =
            categoriesHolder.category

}