package com.briak.newsclient.model.di.application

import com.briak.newsclient.model.di.allnews.AllNewsComponent
import com.briak.newsclient.model.di.topnews.TopNewsComponent
import com.briak.newsclient.presentation.about.AboutPresenter
import com.briak.newsclient.presentation.categories.CategoriesPresenter
import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    (ApplicationModule::class),
    (NavigationModule::class),
    (NetworkModule::class)]
)
@Singleton
interface ApplicationComponent {
    fun topNewsComponentBuilder(): TopNewsComponent.Builder
    fun allNewsComponentBuilder(): AllNewsComponent.Builder

    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
    fun inject(presenter: AboutPresenter)
    fun inject(presenter: CategoriesPresenter)
}