package com.briak.newsclient.model.di.application

import android.content.Context
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.data.server.NewsApi
import com.briak.newsclient.model.di.news.NewsComponent
import com.briak.newsclient.model.di.news.NewsModule
import com.briak.newsclient.model.di.news.NewsNavigationComponent
import com.briak.newsclient.model.di.news.NewsNavigationModule
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.presentation.about.AboutPresenter
import com.briak.newsclient.presentation.categories.CategoriesPresenter
import com.briak.newsclient.presentation.main.MainPresenter
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.ui.main.MainActivity
import com.briak.newsclient.ui.news.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    (ApplicationModule::class),
    (NavigationModule::class),
    (NetworkModule::class)]
)
@Singleton
interface ApplicationComponent {
    fun plusNewsComponent(newsModule: NewsModule): NewsComponent
    fun plusNewsNavigationComponent(newsNavigationModule: NewsNavigationModule): NewsNavigationComponent

    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
    fun inject(presenter: AboutPresenter)
    fun inject(presenter: CategoriesPresenter)
}