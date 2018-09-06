package com.briak.newsclient.presentation.newsdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.di.topnews.TopNewsRouter
import com.briak.newsclient.model.di.topnews.TopNewsScope
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

@InjectViewState
@TopNewsScope
class NewsDetailPresenter @Inject constructor(
        private val newsCicerone: Cicerone<TopNewsRouter>
) : MvpPresenter<NewsDetailView>() {

    fun onBackPressed() {
        newsCicerone.router.exit()
    }
}