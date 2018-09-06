package com.briak.newsclient.model.domain.topnews

import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.di.topnews.TopNewsScope
import com.briak.newsclient.model.repositories.news.NewsRepository
import javax.inject.Inject

@TopNewsScope
class TopNewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository,
        private var categoriesHolder: CategoriesHolder
) : TopNewsInteractor {

    override suspend fun getTopNews(): List<Article> =
            repository.getTopNews("us").await().articles

    override fun setCategory(category: String) {
        categoriesHolder.category = category
    }

    override fun getCategory(): String =
            categoriesHolder.category

}