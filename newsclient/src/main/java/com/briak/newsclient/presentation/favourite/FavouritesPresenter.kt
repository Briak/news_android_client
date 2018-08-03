package com.briak.newsclient.presentation.favourite

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.system.Screens
import ru.terrakok.cicerone.Router

@InjectViewState
class FavouritesPresenter(private var router: Router): MvpPresenter<FavouritesView>() {
    fun onFavouritesClick(id: String) = router.navigateTo(Screens.FAVOURITES_DETAIL_SCREEN)
    fun onBackPressed() = router.exit()
}