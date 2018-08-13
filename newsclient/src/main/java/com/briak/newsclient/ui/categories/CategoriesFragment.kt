package com.briak.newsclient.ui.categories

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.briak.newsclient.R
import com.briak.newsclient.entities.news.presentation.Category
import com.briak.newsclient.extensions.onClick
import com.briak.newsclient.presentation.categories.CategoriesPresenter
import com.briak.newsclient.presentation.categories.CategoriesView
import com.briak.newsclient.ui.base.BaseFragment
import com.briak.newsclient.ui.base.RouterProvider
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment:
        BaseFragment(),
        CategoryAdapter.OnCategoryClickListener,
        CategoriesView {

    @InjectPresenter
    lateinit var presenter: CategoriesPresenter

    override val layoutRes: Int = R.layout.fragment_categories

    @ProvidePresenter
    fun provideCategoriesPresenter(): CategoriesPresenter = CategoriesPresenter((parentFragment as RouterProvider).getRouter())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeView.onClick {
            presenter.back()
        }

        val itemDecorator = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.decorator_listview)!!)

        categoriesListView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(itemDecorator)
            adapter = CategoryAdapter(this@CategoriesFragment)
        }
    }

    override fun onCategoryClick(category: Category) {
        presenter.onCategoryClick(category)
    }
}