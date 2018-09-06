package com.briak.newsclient.model.di.topnews

import com.briak.newsclient.ui.categories.CategoriesFragment
import com.briak.newsclient.ui.topnews.TopNewsFragment
import com.briak.newsclient.ui.newsdetail.NewsDetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [(TopNewsModule::class)])

@TopNewsScope
interface TopNewsComponent {
    fun inject(fragment: NewsDetailFragment)
    fun inject(fragment: TopNewsFragment)
    fun inject(fragment: CategoriesFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): TopNewsComponent
    }
}
