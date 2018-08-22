package com.briak.newsclient.model.di.news

import com.briak.newsclient.ui.categories.CategoriesFragment
import com.briak.newsclient.ui.news.NewsFragment
import com.briak.newsclient.ui.newsdetail.NewsDetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [(NewsModule::class)])

@NewsScope
interface NewsComponent {
    fun inject(fragment: NewsDetailFragment)
    fun inject(fragment: NewsFragment)
    fun inject(fragment: CategoriesFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): NewsComponent
    }
}
