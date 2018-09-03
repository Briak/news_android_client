package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.extensions.asyncTask
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.di.news.NewsScope
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
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

    private lateinit var newsJob: Job

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        getTopNews()
    }

    override fun onDestroy() {
        newsJob.cancel()

        super.onDestroy()
    }

    fun onNewsClick(news: ArticleUI) = newsCicerone.router.navigateTo(Screens.NEWS_DETAIL_SCREEN, news)

    fun onFilterClick() = newsCicerone.router.navigateTo(Screens.CATEGORIES_SCREEN)

    fun onBackPressed() = newsCicerone.router.exit()

    fun setCategory(category: String) {
        newsInteractor.setCategory(category)

        getTopNews()
    }

    private fun getTopNews() {
        viewState.setTitle(newsInteractor.getCategory())

        getTopNews(false)
    }

    fun getTopNews(refresh: Boolean) {
        newsJob = launch(UI) {
            topNews(refresh)
        }
    }

    private suspend fun topNews(refresh: Boolean) {
        viewState.showProgress(!refresh)

        try {
            asyncTask { newsInteractor.getTopNews() }
                    .let { list ->
                        viewState.showTopNews(articleMapper.map(list.articles))
                        viewState.showProgress(false)
                    }
        } catch (e: Throwable) {
            viewState.showMessage(errorHandler.proceed(e))
        }
    }
}