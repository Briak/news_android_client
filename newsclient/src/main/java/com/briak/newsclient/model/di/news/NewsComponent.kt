package com.briak.newsclient.model.di.news

import com.briak.newsclient.model.di.application.ApplicationComponent
import com.briak.newsclient.presentation.newsdetail.NewsDetailPresenter
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [(NewsModule::class)])

@NewsScope
interface NewsComponent {
//    fun inject(presenter: NewsDetailPresenter)
}
