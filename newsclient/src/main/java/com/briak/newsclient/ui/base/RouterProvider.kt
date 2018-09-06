package com.briak.newsclient.ui.base

import com.briak.newsclient.model.di.topnews.TopNewsRouter

interface RouterProvider {
    fun getRouter(): TopNewsRouter
}