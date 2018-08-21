package com.briak.newsclient.presentation.about

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AboutPresenter: MvpPresenter<AboutView>() {
    @Inject lateinit var cicerone: Cicerone<Router>

    fun onBackPressed() {
        cicerone.router.exit()
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}