package com.briak.newsclient.presentation.newsdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.di.allnews.AllNewsRouter
import com.briak.newsclient.model.di.topnews.TopNewsRouter
import ru.terrakok.cicerone.BaseRouter

@InjectViewState
class NewsDetailPresenter : MvpPresenter<NewsDetailView>() {
    private lateinit var router: BaseRouter

    fun setRouter(router: BaseRouter) {
        this.router = router
    }

    fun onBackPressed() {
        when (router) {
            is TopNewsRouter -> (router as TopNewsRouter).exit()
            is AllNewsRouter -> (router as AllNewsRouter).exit()
        }
    }
}