package com.briak.newsclient.model.di.favourites

import com.briak.newsclient.model.di.application.ApplicationComponent
import com.briak.newsclient.presentation.favouritesdetail.FavouritesDetailPresenter
import com.briak.newsclient.ui.favourites.FavouritesFragment
import dagger.Component

@Component(dependencies = [(ApplicationComponent::class)], modules = [(FavouritesNavigationModule::class)])
@FavouritesNavigationScope
interface FavouritesNavigationComponent {
    fun inject(fragment: FavouritesFragment)
    fun inject(presenter: FavouritesDetailPresenter)
}
