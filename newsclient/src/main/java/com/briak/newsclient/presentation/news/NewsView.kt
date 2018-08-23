package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.briak.newsclient.entities.news.server.Article

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsView: MvpView {
    fun setTitle(title: String)
    fun showTopNews(articles: List<Article>)
    fun showProgress(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}