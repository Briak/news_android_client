package com.briak.newsclient.presentation.allnews

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.briak.newsclient.entities.news.presentation.ArticleUI
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface AllNewsView: MvpView {
    fun showAllNews(articles: List<ArticleUI>)
    fun showProgress(show: Boolean)
    fun showEmpty(show: Boolean)
    fun showCalendar(calendar: Calendar)
    fun setTitle(title: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun startNewsJob()
}