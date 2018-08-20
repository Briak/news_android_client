package com.briak.newsclient.model.di.application

import android.content.Context
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.data.storage.Preferences
import com.briak.newsclient.model.di.news.NewsComponent
import com.briak.newsclient.model.domain.categories.CategoriesInteractor
import com.briak.newsclient.model.domain.categories.CategoriesInteractorImpl
import com.briak.newsclient.model.system.ResourceManager
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module(subcomponents = [(NewsComponent::class)])
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

}