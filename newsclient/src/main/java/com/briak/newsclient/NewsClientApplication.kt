package com.briak.newsclient

import android.app.Application
import com.briak.newsclient.model.di.application.ApplicationComponent
import com.briak.newsclient.model.di.application.ApplicationModule
import com.briak.newsclient.model.di.application.DaggerApplicationComponent
import com.briak.newsclient.model.di.news.*

class NewsClientApplication : Application() {
    companion object {
        lateinit var component: ApplicationComponent
        var newsComponent: NewsComponent? = null
        var newsNavigationComponent: NewsNavigationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    fun plusNewsNavigationComponent(): NewsNavigationComponent =
            if (newsNavigationComponent == null) {
                component.plusNewsNavigationComponent(NewsNavigationModule())
            } else {
                newsNavigationComponent!!
            }


}