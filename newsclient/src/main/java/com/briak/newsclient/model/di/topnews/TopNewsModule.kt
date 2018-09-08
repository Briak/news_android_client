package com.briak.newsclient.model.di.topnews

import com.arellomobile.mvp.MvpPresenter
import com.briak.newsclient.model.domain.categories.CategoriesInteractor
import com.briak.newsclient.model.domain.categories.CategoriesInteractorImpl
import com.briak.newsclient.model.domain.topnews.TopNewsInteractor
import com.briak.newsclient.model.domain.topnews.TopNewsInteractorImpl
import com.briak.newsclient.model.repositories.news.NewsRepository
import com.briak.newsclient.model.repositories.news.NewsRepositoryImpl
import com.briak.newsclient.presentation.categories.CategoriesPresenter
import com.briak.newsclient.presentation.categories.CategoriesView
import com.briak.newsclient.presentation.topnews.TopNewsPresenter
import com.briak.newsclient.presentation.topnews.TopNewsView
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone

@Module
abstract class TopNewsModule {
    @Binds
    @TopNewsScope
    abstract fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

    @Binds
    @TopNewsScope
    abstract fun provideTopNewsInteractor(newsInteractor: TopNewsInteractorImpl): TopNewsInteractor

    @Binds
    @TopNewsScope
    abstract fun provideTopNewsPresenter(newsPresenter: TopNewsPresenter): MvpPresenter<TopNewsView>

    @Binds
    @TopNewsScope
    abstract fun provideCategoriesInteractor(categoriesInteractor: CategoriesInteractorImpl): CategoriesInteractor

    @Binds
    @TopNewsScope
    abstract fun provideCategoriesPresenter(categoriesPresenter: CategoriesPresenter): MvpPresenter<CategoriesView>

    @Module
    companion object {
        @JvmStatic
        @Provides
        @TopNewsScope
        fun provideTopNewsRouter(): TopNewsRouter = TopNewsRouter()

        @JvmStatic
        @Provides
        @TopNewsScope
        fun provideTopNewsCicerone(newsRouter: TopNewsRouter): Cicerone<TopNewsRouter> = Cicerone.create(newsRouter)
    }
}