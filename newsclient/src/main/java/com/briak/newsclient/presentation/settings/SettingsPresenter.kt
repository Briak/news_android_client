package com.briak.newsclient.presentation.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.ui.settings.SettingsView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SettingsPresenter: MvpPresenter<SettingsView>() {
    @Inject lateinit var router: Router

    fun onBackPressed() {
        router.exit()
    }

    init {
        NewsClientApplication.component.inject(this)
    }

}