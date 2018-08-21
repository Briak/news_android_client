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

@Module
abstract class NewsModule {
    @Binds
    @NewsScope
    abstract fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    @NewsScope
    abstract fun provideNewsInteractor(newsInteractor: NewsInteractorImpl): NewsInteractor

    @Binds
    @NewsScope
    abstract fun provideNewsPresenter(newsPresenter: NewsPresenter): MvpPresenter<NewsView>

    @Module
    companion object {
        @JvmStatic
        @Provides
        @NewsScope
        fun provideNewsRouter(): NewsRouter = NewsRouter()

        @JvmStatic
        @Provides
        @NewsScope
        fun provideNewsCicerone(newsRouter: NewsRouter): Cicerone<NewsRouter> = Cicerone.create(newsRouter)
    }
}