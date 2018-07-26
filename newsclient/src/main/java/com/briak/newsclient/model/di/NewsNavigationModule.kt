package com.briak.newsclient.model.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
class NewsNavigationModule {
    @Provides
    @NewsNavigationScope
    fun provideNewsRouter(): Router = Cicerone.create().router
}