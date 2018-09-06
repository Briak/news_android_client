package com.briak.newsclient.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.model.system.Screens
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val cicerone: Cicerone<Router>
) : MvpPresenter<MainView>() {

    fun onTopNewsTabClick() {
        cicerone.router.replaceScreen(Screens.TOP_NEWS_SCREEN)
    }

    fun onAllNewsTabClick() {
        cicerone.router.replaceScreen(Screens.ALL_NEWS_SCREEN)
    }

    fun onAboutTabClick() {
        cicerone.router.replaceScreen(Screens.SETTINGS_SCREEN)
    }

    fun onBackPressed() {
        cicerone.router.exit()
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}