package com.briak.newsclient.model.di.application

import android.content.Context
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.data.server.NewsApi
import com.briak.newsclient.model.data.storage.Preferences
import com.briak.newsclient.model.domain.categories.CategoriesInteractor
import com.briak.newsclient.model.domain.categories.CategoriesInteractorImpl
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.model.domain.news.NewsInteractorImpl
import com.briak.newsclient.model.repositories.news.NewsRepository
import com.briak.newsclient.model.repositories.news.NewsRepositoryImpl
import com.briak.newsclient.model.system.ResourceManager
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @NotNull
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideResourceManager(context: Context) = ResourceManager(context)

    @Provides
    @Singleton
    fun providePreferences(context: Context): CategoriesHolder =
            Preferences(context)

    @Provides
    @Singleton
    fun provideCategoriesInteractor(categoriesHolder: CategoriesHolder): CategoriesInteractor =
            CategoriesInteractorImpl(categoriesHolder)

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi, categoriesHolder: CategoriesHolder): NewsRepository =
            NewsRepositoryImpl(api, categoriesHolder)

    @Provides
    @Singleton
    fun provideNewsInteractor(repository: NewsRepository, categoriesHolder: CategoriesHolder): NewsInteractor =
            NewsInteractorImpl(repository, categoriesHolder)

}