package com.briak.newsclient.presentation.categories

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.entities.news.presentation.CategoryUI
import com.briak.newsclient.model.di.topnews.TopNewsRouter
import com.briak.newsclient.model.di.topnews.TopNewsScope
import com.briak.newsclient.model.domain.categories.CategoriesInteractor
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

@InjectViewState
@TopNewsScope
class CategoriesPresenter @Inject constructor(
        private val newsCicerone: Cicerone<TopNewsRouter>,
        private val categoriesInteractor: CategoriesInteractor
) : MvpPresenter<CategoriesView>() {

    fun setSelectedCategory() {
        viewState.setSelectedCategory(categoriesInteractor.getCategory())
    }

    fun back() {
        newsCicerone.router.exit()
    }

    fun onCategoryClick(category: CategoryUI) {
        newsCicerone.router.exitWithResult(1001, category)
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}