package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.entities.news.server.Article
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

@InjectViewState
class NewsPresenter @Inject constructor(private var newsInteractor: NewsInteractor) : MvpPresenter<NewsView>() {

    @Inject
    lateinit var errorHandler: ErrorHandler

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        getTopNews()
    }

    fun onNewsClick(news: Article) = newsInteractor.onNewsClick(news)

    fun onFilterClick() = newsInteractor.onFilterClick()

    fun onBackPressed() = newsInteractor.onBackPressed()

    fun setCategory(category: String) {
        newsInteractor.setCategory(category)

        getTopNews()
    }

    private fun getTopNews() {
        viewState.setTitle(newsInteractor.getCategory())

        getTopNews(false)
    }

    fun getTopNews(refresh: Boolean) {
        launch(UI) {
            viewState.showProgress(!refresh)

            val request = async { newsInteractor.getTopNews() }
            try {
                val result = request.await()
                viewState.showTopNews(result.await().articles)
                viewState.showProgress(false)
            } catch (e: Throwable) {
                e.printStackTrace()
                viewState.showMessage(errorHandler.proceed(e))
                viewState.showProgress(false)
            }
        }
    }
}