package com.briak.newsclient.presentation.topnews

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.model.di.topnews.TopNewsRouter
import com.briak.newsclient.model.di.topnews.TopNewsScope
import com.briak.newsclient.model.domain.topnews.TopNewsInteractor
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

@InjectViewState
@TopNewsScope
class TopNewsPresenter @Inject constructor(
        private val topNewsInteractor: TopNewsInteractor,
        private val topNewsCicerone: Cicerone<TopNewsRouter>,
        private val errorHandler: ErrorHandler,
        private val articleMapper: ArticleMapper
) : MvpPresenter<TopNewsView>() {

    private var refresh: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setTitle(topNewsInteractor.getCategory())
        viewState.startNewsJob()
    }

    fun onNewsClick(news: ArticleUI) = topNewsCicerone.router.navigateTo(Screens.NEWS_DETAIL_SCREEN, news)

    fun onFilterClick() = topNewsCicerone.router.navigateTo(Screens.CATEGORIES_SCREEN)

    fun onBackPressed() = topNewsCicerone.router.exit()

    fun setCategory(category: String) {
        topNewsInteractor.setCategory(category)
        viewState.setTitle(topNewsInteractor.getCategory())

        viewState.startNewsJob()
    }

    fun refresh() {
        refresh = true
        viewState.startNewsJob()
    }

    suspend fun getTopNews() {
        viewState.showProgress(!refresh)

        try {
            withContext(CommonPool) {
                topNewsInteractor.getTopNews()
            }.let { articles ->
                viewState.showTopNews(articleMapper.map(articles))
                viewState.showEmpty(articles.isEmpty())
                viewState.showProgress(false)

                refresh = false
            }
        } catch (e: Throwable) {
            viewState.showMessage(errorHandler.proceed(e))

            refresh = false
        }
    }
}