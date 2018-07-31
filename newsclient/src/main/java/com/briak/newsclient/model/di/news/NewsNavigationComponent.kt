package com.briak.newsclient.model.di.news

import com.briak.newsclient.model.di.application.ApplicationComponent
import com.briak.newsclient.presentation.newsdetail.NewsDetailPresenter
import com.briak.newsclient.ui.news.NewsFragment
import dagger.Component

@Component(dependencies = [(ApplicationComponent::class)], modules = [(NewsNavigationModule::class)])
@NewsNavigationScope
interface NewsNavigationComponent {
    fun inject(fragment: NewsFragment)
    fun inject(presenter: NewsDetailPresenter)
}
