package com.briak.newsclient.presentation.topnews

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.briak.newsclient.entities.news.presentation.ArticleUI

@StateStrategyType(AddToEndSingleStrategy::class)
interface TopNewsView: MvpView {
    fun setTitle(title: String)
    fun showTopNews(articles: List<ArticleUI>)
    fun showProgress(show: Boolean)
    fun showEmpty(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun startNewsJob()
}