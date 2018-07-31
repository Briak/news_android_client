package com.briak.newsclient

import android.app.Application
import com.briak.newsclient.model.di.application.ApplicationComponent
import com.briak.newsclient.model.di.application.ApplicationModule
import com.briak.newsclient.model.di.application.DaggerApplicationComponent
import com.briak.newsclient.model.di.favourites.DaggerFavouritesNavigationComponent
import com.briak.newsclient.model.di.favourites.FavouritesNavigationComponent
import com.briak.newsclient.model.di.news.DaggerNewsNavigationComponent
import com.briak.newsclient.model.di.news.NewsNavigationComponent

class NewsClientApplication : Application() {
    companion object {
        lateinit var component: ApplicationComponent
        lateinit var newsNavigationComponent: NewsNavigationComponent
        lateinit var favouritesNavigationComponent: FavouritesNavigationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

        newsNavigationComponent = DaggerNewsNavigationComponent
                .builder()
                .applicationComponent(component)
                .build()

        favouritesNavigationComponent = DaggerFavouritesNavigationComponent
                .builder()
                .applicationComponent(component)
                .build()
    }
}