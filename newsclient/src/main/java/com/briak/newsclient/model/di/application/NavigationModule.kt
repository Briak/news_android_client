package com.briak.newsclient.model.di.application

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()
}