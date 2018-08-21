package com.briak.newsclient.presentation.categories

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.NewsClientApplication
import com.briak.newsclient.entities.news.presentation.Category
import com.briak.newsclient.model.di.news.NewsRouter
import com.briak.newsclient.model.domain.categories.CategoriesInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class CategoriesPresenter(private var router: NewsRouter): MvpPresenter<CategoriesView>() {

    @Inject
    lateinit var categoriesInteractor: CategoriesInteractor

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setSelectedCategory(categoriesInteractor.getCategory())
    }

    fun back() {
        router.exit()
    }

    fun onCategoryClick(category: Category) {
        router.exitWithResult(1001, category)
    }

    init {
        NewsClientApplication.component.inject(this)
    }
}