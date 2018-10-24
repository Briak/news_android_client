package com.briak.newsclient.presentation.topnews

import com.arellomobile.mvp.InjectViewState
import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.model.di.topnews.TopNewsRouter
import com.briak.newsclient.model.di.topnews.TopNewsScope
import com.briak.newsclient.model.domain.topnews.TopNewsInteractor
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.BasePresenter
import com.briak.newsclient.presentation.base.ErrorHandler
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

@InjectViewState
@TopNewsScope
class TopNewsPresenter @Inject constructor(
        private val topNewsInteractor: TopNewsInteractor,
        private val topNewsCicerone: Cicerone<TopNewsRouter>,
        private val errorHandler: ErrorHandler,
        private val articleMapper: ArticleMapper
) : BasePresenter<TopNewsView>() {

    private var refresh: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setTitle(topNewsInteractor.getCategory())
        getTopNews()
    }

    fun onNewsClick(news: ArticleUI) = topNewsCicerone.router.navigateTo(Screens.NEWS_DETAIL_SCREEN, news)

    fun onFilterClick() = topNewsCicerone.router.navigateTo(Screens.CATEGORIES_SCREEN)

    fun onBackPressed() = topNewsCicerone.router.exit()

    fun setCategory(category: String) {
        topNewsInteractor.setCategory(category)
        viewState.setTitle(topNewsInteractor.getCategory())

        getTopNews()
    }

    fun refresh() {
        refresh = true
        getTopNews()
    }

    fun getTopNews() {
        topNewsInteractor
                .getTopNews()
                .doOnSubscribe { viewState.showProgress(!refresh) }
                .doAfterTerminate {
                    viewState.showProgress(false)
                    refresh = false
                }
                .subscribe(
                        { rss ->
                            viewState.showTopNews(articleMapper.map(rss.articles))
                            viewState.showEmpty(rss.articles.isEmpty())
                        },
                        { message ->
                            errorHandler.proceed(message) { viewState.showMessage(it) }
                        }
                )
                .connect()
    }
}