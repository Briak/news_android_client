package com.briak.newsclient.presentation.newsdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.ui.newsdetail.NewsDetailView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewsDetailPresenter: MvpPresenter<NewsDetailView>() {

    @Inject
    lateinit var newsRouter: Router

    fun onBackPressed() {
        newsRouter.exit()
    }

    init {
        NewsClientApplication.newsNavigationComponent.inject(this)
    }

}