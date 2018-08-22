package com.briak.newsclient.model.di.news

import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.domain.categories.CategoriesInteractor
import com.briak.newsclient.model.domain.categories.CategoriesInteractorImpl
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.model.domain.news.NewsInteractorImpl
import com.briak.newsclient.model.repositories.news.NewsRepository
import com.briak.newsclient.model.repositories.news.NewsRepositoryImpl
import com.briak.newsclient.presentation.categories.CategoriesPresenter
import com.briak.newsclient.presentation.categories.CategoriesView
import com.briak.newsclient.presentation.news.NewsPresenter
import com.briak.newsclient.presentation.news.NewsView
import com.briak.newsclient.presentation.newsdetail.NewsDetailPresenter
import com.briak.newsclient.presentation.newsdetail.NewsDetailView
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

    @Binds
    @NewsScope
    abstract fun provideNewsDetailPresenter(newsDetailPresenter: NewsDetailPresenter): MvpPresenter<NewsDetailView>

    @Binds
    @NewsScope
    abstract fun provideCategoriesInteractor(categoriesInteractor: CategoriesInteractorImpl): CategoriesInteractor

    @Binds
    @NewsScope
    abstract fun provideCategoriesPresenter(categoriesPresenter: CategoriesPresenter): MvpPresenter<CategoriesView>

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