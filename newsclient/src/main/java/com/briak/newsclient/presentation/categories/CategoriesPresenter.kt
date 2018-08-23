package com.briak.newsclient.presentation.categories

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.entities.news.presentation.CategoryUI
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.di.news.NewsScope
import com.briak.newsclient.model.domain.categories.CategoriesInteractor
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

@InjectViewState
@NewsScope
class CategoriesPresenter @Inject constructor(
        private val newsCicerone: Cicerone<NewsRouter>,
        private val categoriesInteractor: CategoriesInteractor
) : MvpPresenter<CategoriesView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

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