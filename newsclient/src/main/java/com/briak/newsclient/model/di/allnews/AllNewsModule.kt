package com.briak.newsclient.model.di.allnews

import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.domain.allnews.AllNewsInteractor
import com.briak.newsclient.model.domain.allnews.AllNewsInteractorImpl
import com.briak.newsclient.model.repositories.news.NewsRepository
import com.briak.newsclient.model.repositories.news.NewsRepositoryImpl
import com.briak.newsclient.presentation.allnews.AllNewsPresenter
import com.briak.newsclient.presentation.allnews.AllNewsView
import com.briak.newsclient.presentation.newsdetail.NewsDetailPresenter
import com.briak.newsclient.presentation.newsdetail.NewsDetailView
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone

@Module
abstract class AllNewsModule {
    @Binds
    @AllNewsScope
    abstract fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    @AllNewsScope
    abstract fun provideAllNewsInteractor(newsInteractor: AllNewsInteractorImpl): AllNewsInteractor

    @Binds
    @AllNewsScope
    abstract fun provideAllNewsPresenter(newsPresenter: AllNewsPresenter): MvpPresenter<AllNewsView>

    @Binds
    @AllNewsScope
    abstract fun provideNewsDetailPresenter(newsDetailPresenter: NewsDetailPresenter): MvpPresenter<NewsDetailView>

    @Module
    companion object {
        @JvmStatic
        @Provides
        @AllNewsScope
        fun provideAllNewsRouter(): AllNewsRouter = AllNewsRouter()

        @JvmStatic
        @Provides
        @AllNewsScope
        fun provideAllNewsCicerone(newsRouter: AllNewsRouter): Cicerone<AllNewsRouter> = Cicerone.create(newsRouter)
    }
}