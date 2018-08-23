package com.briak.newsclient.model.domain.categories

import com.briak.newsclient.model.data.categories.CategoriesHolder
import javax.inject.Inject

class CategoriesInteractorImpl @Inject constructor(
        private var categoriesHolder: CategoriesHolder
) : CategoriesInteractor {

    override fun getCategory(): String = categoriesHolder.category
}