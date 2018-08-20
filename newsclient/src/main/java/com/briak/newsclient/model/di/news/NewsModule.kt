package com.briak.newsclient.model.di.news

import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.model.domain.news.NewsInteractorImpl
import com.briak.newsclient.model.repositories.news.NewsRepository
import com.briak.newsclient.model.repositories.news.NewsRepositoryImpl
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.presentation.news.NewsView
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
interface NewsModule {
    @Binds
    @NewsScope
    fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    @NewsScope
    fun provideNewsInteractor(newsInteractor: NewsInteractorImpl): NewsInteractor

    @Binds
    @NewsScope
    fun provideNewsPresenter(newsPresenter: NewsPresenter): MvpPresenter<NewsView>

    @Module
    companion object {
        private val newsCicerone: Cicerone<Router> = Cicerone.create()

        @Provides
        @NewsScope
        fun provideNewsRouter(): Router = newsCicerone.router

        @Provides
        @NewsScope
        fun provideNewsNavigatorHolder(): NavigatorHolder = newsCicerone.navigatorHolder
    }
}