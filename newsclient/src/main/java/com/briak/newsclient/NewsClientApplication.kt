package com.briak.newsclient

import android.app.Application
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Cicerone



class NewsClientApplication : Application() {
    private lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()

        initCicerone()
    }

    private fun initCicerone() {
        cicerone = Cicerone.create()
    }

    fun navigationHolder(): NavigatorHolder = cicerone.navigatorHolder

    fun router(): Router = cicerone.router
}