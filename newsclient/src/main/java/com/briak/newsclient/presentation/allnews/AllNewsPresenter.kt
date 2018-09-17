package com.briak.newsclient.presentation.allnews

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.extensions.backgroundPool
import com.briak.newsclient.extensions.isNotNullOrEmpty
import com.briak.newsclient.extensions.toServerDate
import com.briak.newsclient.extensions.toUserDate
import com.briak.newsclient.model.di.allnews.AllNewsRouter
import com.briak.newsclient.model.domain.allnews.AllNewsInteractor
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.ErrorHandler
import kotlinx.coroutines.experimental.withContext
import ru.terrakok.cicerone.Cicerone
import java.util.*
import javax.inject.Inject

@InjectViewState
class AllNewsPresenter @Inject constructor(
        private var allNewsInteractor: AllNewsInteractor,
        private var allNewsCicerone: Cicerone<AllNewsRouter>,
        private val errorHandler: ErrorHandler,
        private val articleMapper: ArticleMapper
) : MvpPresenter<AllNewsView>() {

    private var calendar: Calendar? = null
    private var query: String? = null
    private var refresh: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        setCalendar(Calendar.getInstance())
    }

    fun onNewsClick(news: ArticleUI) = allNewsCicerone.router.navigateTo(Screens.ALL_NEWS_DETAIL_SCREEN, news)

    fun onBackPressed() = allNewsCicerone.router.exit()

    fun showCalendar() {
        viewState.showCalendar(calendar!!)
    }

    fun setCalendar(calendar: Calendar) {
        if (this.calendar != calendar) {
            this.calendar = calendar
            viewState.setTitle(calendar.time.toUserDate())
            viewState.startNewsJob()
        }
    }

    fun setSearchQuery(query: String?) {
        if (query.isNotNullOrEmpty()) {
            if (query!!.length >= 2) {
                setQuery(query)
            } else if (query.isNotEmpty()) {
                setQuery(null)
            }
        }
    }

    fun setQuery(query: String?) {
        if (this.query != query) {
            this.query = query
            viewState.startNewsJob()
        }
    }

    fun refresh() {
        refresh = true
        viewState.startNewsJob()
    }

    suspend fun getAllNews() {
        viewState.showProgress(!refresh)

        try {
            withContext(backgroundPool) {
                allNewsInteractor.getAllNews(query, formatDate())
            }.let { articles ->
                viewState.showAllNews(articleMapper.map(articles))
                viewState.showEmpty(articles.isEmpty())
                viewState.showProgress(false)

                refresh = false
            }
        } catch (e: Throwable) {
            viewState.showMessage(errorHandler.proceed(e))
            refresh = false
        }
    }

    private fun formatDate(): String =
            calendar!!.time.toServerDate()
}