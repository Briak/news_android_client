package com.briak.newsclient.model.domain.topnews

import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.di.topnews.TopNewsScope
import com.briak.newsclient.model.repositories.news.NewsRepository
import io.reactivex.Single
import javax.inject.Inject

@TopNewsScope
class TopNewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository,
        private var categoriesHolder: CategoriesHolder
) : TopNewsInteractor {

    override fun getTopNews(): Single<RSS> =
            repository.getTopNews("us")

    override fun setCategory(category: String) {
        categoriesHolder.category = category
    }

    override fun getCategory(): String =
            categoriesHolder.category

}