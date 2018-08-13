package com.briak.newsclient.ui.base

import ru.terrakok.cicerone.Router

interface RouterProvider {
    fun getRouter(): Router
}