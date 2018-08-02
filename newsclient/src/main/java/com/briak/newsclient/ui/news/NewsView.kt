package com.briak.newsclient.ui.news

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.briak.newsclient.entities.news.server.Article

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsView: MvpView {
    fun showTopNews(articles: List<Article>)
}