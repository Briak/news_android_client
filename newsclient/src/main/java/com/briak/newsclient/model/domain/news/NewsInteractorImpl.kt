package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.di.news.NewsScope
import com.briak.newsclient.model.repositories.news.NewsRepository
import javax.inject.Inject

@NewsScope
class NewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository,
        private var categoriesHolder: CategoriesHolder
) : NewsInteractor {

    override suspend fun getTopNews(): RSS =
            repository.getNews("us").await()

    override fun setCategory(category: String) {
        categoriesHolder.category = category
    }

    override fun getCategory(): String =
            categoriesHolder.category

    override suspend fun doSomething(): Int = 4

}