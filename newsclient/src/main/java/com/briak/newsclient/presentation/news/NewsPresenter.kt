package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.extensions.backgroundPool
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.di.news.NewsScope
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.withContext
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

@InjectViewState
@NewsScope
class NewsPresenter @Inject constructor(
        private val newsInteractor: NewsInteractor,
        private val newsCicerone: Cicerone<NewsRouter>,
        private val errorHandler: ErrorHandler,
        private val articleMapper: ArticleMapper
) : MvpPresenter<NewsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setTitle(newsInteractor.getCategory())
        viewState.startNewsJob(false)
    }

    fun onNewsClick(news: ArticleUI) = newsCicerone.router.navigateTo(Screens.NEWS_DETAIL_SCREEN, news)

    fun onFilterClick() = newsCicerone.router.navigateTo(Screens.CATEGORIES_SCREEN)

    fun onBackPressed() = newsCicerone.router.exit()

    fun setCategory(category: String) {
        newsInteractor.setCategory(category)
        viewState.setTitle(newsInteractor.getCategory())

        viewState.startNewsJob(false)
    }

    suspend fun getTopNews(refresh: Boolean) {
        viewState.showProgress(!refresh)

        try {
            withContext(backgroundPool) {
                newsInteractor.getTopNews()
            }.let { articles ->
                viewState.showTopNews(articleMapper.map(articles))
                viewState.showEmpty(articles.isEmpty())
                viewState.showProgress(false)
            }
        } catch (e: Throwable) {
            viewState.showMessage(errorHandler.proceed(e))
        }
    }
}