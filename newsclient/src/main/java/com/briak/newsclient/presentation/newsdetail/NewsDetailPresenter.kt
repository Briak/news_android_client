package com.briak.newsclient.presentation.newsdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.di.news.NewsScope
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

@InjectViewState
@NewsScope
class NewsDetailPresenter @Inject constructor(
        private val newsCicerone: Cicerone<NewsRouter>
) : MvpPresenter<NewsDetailView>() {

    fun onBackPressed() {
        newsCicerone.router.exit()
    }
}