package com.briak.newsclient.presentation.allnews

import com.arellomobile.mvp.InjectViewState
import com.briak.newsclient.entities.mapper.ArticleMapper
import com.briak.newsclient.entities.news.presentation.ArticleUI
import com.briak.newsclient.extensions.isNotNullOrEmpty
import com.briak.newsclient.extensions.toServerDate
import com.briak.newsclient.extensions.toUserDate
import com.briak.newsclient.model.di.allnews.AllNewsRouter
import com.briak.newsclient.model.domain.allnews.AllNewsInteractor
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.presentation.base.BasePresenter
import com.briak.newsclient.presentation.base.ErrorHandler
import ru.terrakok.cicerone.Cicerone
import java.util.*
import javax.inject.Inject

@InjectViewState
class AllNewsPresenter @Inject constructor(
        private var allNewsInteractor: AllNewsInteractor,
        private var allNewsCicerone: Cicerone<AllNewsRouter>,
        private val errorHandler: ErrorHandler,
        private val articleMapper: ArticleMapper
) : BasePresenter<AllNewsView>() {

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
            getAllNews()
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
            getAllNews()
        }
    }

    fun refresh() {
        refresh = true
        getAllNews()
    }

    fun getAllNews() {
        allNewsInteractor
                .getAllNews(query, formatDate())
                .doOnSubscribe { viewState.showProgress(!refresh) }
                .doAfterTerminate {
                    viewState.showProgress(false)
                    refresh = false
                }
                .subscribe(
                        { rss ->
                            viewState.showAllNews(articleMapper.map(rss.articles))
                            viewState.showEmpty(rss.articles.isEmpty())

                        },
                        { message ->
                            errorHandler.proceed(message) { viewState.showMessage(it) }
                        }
                )
                .connect()
    }

    private fun formatDate(): String? =
            calendar?.time?.toServerDate()
}