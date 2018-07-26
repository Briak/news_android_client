package com.briak.newsclient.model.di

import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.ui.news.NewsFragment
import dagger.Component

@Component(dependencies = [(ApplicationComponent::class)], modules = [(NewsNavigationModule::class)])
@NewsNavigationScope
interface NewsNavigationComponent {
    fun inject(presenter: NewsPresenter)
    fun inject(fragment: NewsFragment)
}
