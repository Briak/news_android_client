package com.briak.newsclient.ui.base

import ru.terrakok.cicerone.BaseRouter

interface RouterProvider {
    fun getRouter(): BaseRouter
}