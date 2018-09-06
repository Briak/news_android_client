package com.briak.newsclient

import android.app.Application
import com.briak.newsclient.model.di.allnews.AllNewsComponent
import com.briak.newsclient.model.di.application.ApplicationComponent
import com.briak.newsclient.model.di.application.ApplicationModule
import com.briak.newsclient.model.di.application.DaggerApplicationComponent
import com.briak.newsclient.model.di.topnews.TopNewsComponent

class NewsClientApplication : Application() {
    companion object {
        lateinit var component: ApplicationComponent
        private var topNewsComponent: TopNewsComponent? = null
        private var allNewsComponent: AllNewsComponent? = null

        fun plusTopNewsComponent(): TopNewsComponent {
            if (topNewsComponent == null) {
                topNewsComponent = component
                        .topNewsComponentBuilder()
                        .build()
            }
            return topNewsComponent!!
        }

        fun plusAllNewsComponent(): AllNewsComponent {
            if (allNewsComponent == null) {
                allNewsComponent = component
                        .allNewsComponentBuilder()
                        .build()
            }

            return allNewsComponent!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}