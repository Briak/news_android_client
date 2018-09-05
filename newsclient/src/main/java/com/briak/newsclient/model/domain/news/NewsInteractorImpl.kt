package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.di.news.NewsScope
import com.briak.newsclient.model.repositories.news.NewsRepository
import javax.inject.Inject

@NewsScope
class NewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository,
        private var categoriesHolder: CategoriesHolder
) : NewsInteractor {

    override suspend fun getTopNews(query: String?): List<Article> =
            repository.getNews("us", query).await().articles

    override fun setCategory(category: String) {
        categoriesHolder.category = category
    }

    override fun getCategory(): String =
            categoriesHolder.category

}