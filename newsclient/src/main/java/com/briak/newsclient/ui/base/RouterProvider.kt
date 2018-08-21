package com.briak.newsclient.ui.base

import com.briak.newsclient.model.di.news.NewsRouter
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Router

interface RouterProvider {
    fun getRouter(): NewsRouter
}