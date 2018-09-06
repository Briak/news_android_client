package com.briak.newsclient.presentation.allnews

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.extensions.backgroundPool
import com.briak.newsclient.model.domain.allnews.AllNewsInteractor
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

@InjectViewState
class AllNewsPresenter @Inject constructor(
        private var allNewsInteractor: AllNewsInteractor,
        private val errorHandler: ErrorHandler,
        private val articleMapper: ArticleMapper
) : MvpPresenter<AllNewsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.startNewsJob(false, null, null)
    }

    suspend fun getAllNews(refresh: Boolean, query: String?, date: String?) {
        viewState.showProgress(!refresh)

        try {
            withContext(backgroundPool) {
                allNewsInteractor.getAllNews(query, date)
            }.let { articles ->
                viewState.showAllNews(articleMapper.map(articles))
                viewState.showEmpty(articles.isEmpty())
                viewState.showProgress(false)
            }
        } catch (e: Throwable) {
            viewState.showMessage(errorHandler.proceed(e))
        }
    }
}