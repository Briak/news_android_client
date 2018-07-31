package com.briak.newsclient.model.di.favourites

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class FavouritesNavigationModule {
    private val favouritesCicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @FavouritesNavigationScope
    fun provideFavouritesRouter(): Router = favouritesCicerone.router

    @Provides
    @FavouritesNavigationScope
    fun provideFavouritesNavigatorHolder(): NavigatorHolder = favouritesCicerone.navigatorHolder
}