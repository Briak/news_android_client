package com.briak.newsclient.presentation.categories

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.entities.news.presentation.Category
import ru.terrakok.cicerone.Router

@InjectViewState
class CategoriesPresenter(private var router: Router): MvpPresenter<CategoriesView>() {

    fun back() {
        router.exit()
    }

    fun onCategoryClick(category: Category) {
        router.exitWithResult(1001, category)
    }
}