package com.briak.newsclient.model.domain.news

import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.entities.news.server.RSS
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.di.news.NewsScope
import com.briak.newsclient.model.repositories.news.NewsRepository
import com.briak.newsclient.model.system.Screens
import kotlinx.coroutines.experimental.Deferred
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@NewsScope
class NewsInteractorImpl @Inject constructor(
        private var repository: NewsRepository,
        private var categoriesHolder: CategoriesHolder,
        private var newsRouter: Router
) : NewsInteractor {

    override fun getTopNews(): Deferred<RSS> =
            repository.getNews("us")

    override fun getAllNews(): Deferred<RSS> =
            repository.getNews("us")

    override fun setCategory(category: String) {
        categoriesHolder.category = category
    }

    override fun getCategory(): String =
            categoriesHolder.category

    override fun onNewsClick(news: Article) {
        newsRouter.navigateTo(Screens.NEWS_DETAIL_SCREEN, news)
    }

    override fun onFilterClick() {
        newsRouter.navigateTo(Screens.CATEGORIES_SCREEN)
    }

    override fun onBackPressed() {
        newsRouter.exit()
    }

}