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

@Module
interface NewsModule {
//    @Provides
//    @NewsNavigationScope
//    fun provideNewsRepository(api: NewsApi, categoriesHolder: CategoriesHolder): NewsRepository =
//            NewsRepositoryImpl(api, categoriesHolder)
//
//    @Provides
//    @NewsNavigationScope
//    fun provideNewsInteractor(
//            repository: NewsRepository,
//            categoriesHolder: CategoriesHolder,
//            newsRouter: Router
//    ): NewsInteractor =
//            NewsInteractorImpl(repository, categoriesHolder, newsRouter)

    @Binds
    @NewsScope
    fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    @NewsScope
    fun provideNewsInteractor(newsInteractor: NewsInteractorImpl): NewsInteractor

    @Binds
    @NewsScope
    fun provideNewsPresenter(newsPresenter: NewsPresenter): MvpPresenter<NewsView>
}