package com.briak.newsclient.model.di.application

import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.presentation.about.AboutPresenter
import com.briak.newsclient.presentation.categories.CategoriesPresenter
import com.briak.newsclient.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(ApplicationModule::class), (NavigationModule::class), (NetworkModule::class)])
@Singleton
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
    fun inject(presenter: AboutPresenter)
    fun inject(presenter: NewsPresenter)
    fun inject(presenter: CategoriesPresenter)
}