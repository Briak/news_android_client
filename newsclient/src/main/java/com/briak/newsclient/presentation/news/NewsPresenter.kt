package com.briak.newsclient.presentation.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.system.Screens
import com.briak.newsclient.ui.news.NewsView
import ru.terrakok.cicerone.Router

@InjectViewState
class NewsPresenter(private var router: Router) : MvpPresenter<NewsView>() {

    fun onNewsClick(id: String) = router.navigateTo(Screens.NEWS_DETAIL_SCREEN)
    fun onBackPressed() = router.exit()
}