package com.briak.newsclient.model.di.news

import com.briak.newsclient.presentation.newsdetail.NewsDetailPresenter
import com.briak.newsclient.ui.news.NewsFragment
import dagger.Subcomponent

@Subcomponent(modules = [(NewsModule::class)])

@NewsScope
interface NewsComponent {
    fun inject(presenter: NewsDetailPresenter)
    fun inject(fragment: NewsFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): NewsComponent
    }
}
