package com.briak.newsclient.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.model.system.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    fun onNewsTabClick() {
        router.replaceScreen(Screens.NEWS_SCREEN)
    }

    fun onAboutTabClick() {
        router.replaceScreen(Screens.SETTINGS_SCREEN)
    }

    fun onBackPressed() {
        router.exit()
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}