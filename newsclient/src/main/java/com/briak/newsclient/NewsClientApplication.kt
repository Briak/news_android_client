package com.briak.newsclient

import android.app.Application
import com.briak.newsclient.model.di.application.ApplicationComponent
import com.briak.newsclient.model.di.application.ApplicationModule
import com.briak.newsclient.model.di.application.DaggerApplicationComponent
import com.briak.newsclient.model.di.news.*

class NewsClientApplication : Application() {
    companion object {
        lateinit var component: ApplicationComponent
        private var newsComponent: NewsComponent? = null

        fun plusNewsComponent(): NewsComponent =
                if (newsComponent == null) {
                    component
                            .newsComponentBuilder()
                            .build()
                } else {
                    newsComponent!!
                }

        fun clearNewsComponent() {
            newsComponent = null
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