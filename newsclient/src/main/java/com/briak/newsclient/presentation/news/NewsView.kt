package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.briak.newsclient.entities.news.presentation.ArticleUI

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsView: MvpView {
    fun setTitle(title: String)
    fun showTopNews(articles: List<ArticleUI>)
    fun showProgress(show: Boolean)
    fun startNewsJob(refresh: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}