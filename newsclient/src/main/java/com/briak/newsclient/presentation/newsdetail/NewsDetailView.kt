package com.briak.newsclient.presentation.newsdetail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsDetailView: MvpView {
    fun setNewsDetailToolbarTitle(value: String)
    fun loadImage(urlToImage: String?, iconId: Int)
    fun setAuthor(value: String)
    fun hideAuthor()
    fun setPublishedAt(date: String)
    fun hidePublishedAt()
    fun setTitle(title: String)
    fun hideTitle()
    fun setDescription(description: String)
    fun hideDescription()

}