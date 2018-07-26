package com.briak.newsclient

import android.app.Application
import com.briak.newsclient.model.di.*

class NewsClientApplication : Application() {
    companion object {
        lateinit var component: ApplicationComponent
        lateinit var newsNavigationComponent: NewsNavigationComponent
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
    }
}