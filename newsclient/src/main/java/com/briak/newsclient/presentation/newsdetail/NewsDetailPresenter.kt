package com.briak.newsclient.presentation.newsdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.model.di.news.NewsRouter
import javax.inject.Inject

@InjectViewState
class NewsDetailPresenter: MvpPresenter<NewsDetailView>() {

    @Inject
    lateinit var newsRouter: NewsRouter

    fun onBackPressed() {
        newsRouter.exit()
    }

    init {
        NewsClientApplication.plusNewsComponent().inject(this)
    }
}