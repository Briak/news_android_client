package com.briak.newsclient.presentation.favouritesdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class FavouritesDetailPresenter: MvpPresenter<FavouritesDetailView>() {
    @Inject
    lateinit var favouritesRouter: Router

    fun onBackPressed() {
        favouritesRouter.exit()
    }

    init {
        NewsClientApplication.favouritesNavigationComponent.inject(this)
    }
}