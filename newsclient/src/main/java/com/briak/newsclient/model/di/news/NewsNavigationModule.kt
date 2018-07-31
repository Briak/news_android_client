package com.briak.newsclient.model.di.news

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class NewsNavigationModule {
    private val newsCicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @NewsNavigationScope
    fun provideNewsRouter(): Router = newsCicerone.router

    @Provides
    @NewsNavigationScope
    fun provideNewsNavigatorHolder(): NavigatorHolder = newsCicerone.navigatorHolder
}