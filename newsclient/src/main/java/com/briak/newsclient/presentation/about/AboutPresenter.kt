package com.briak.newsclient.presentation.about

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AboutPresenter: MvpPresenter<AboutView>() {
    @Inject lateinit var router: Router

    fun onBackPressed() {
        router.exit()
    }

    init {
        NewsClientApplication.component.inject(this)
    }

}