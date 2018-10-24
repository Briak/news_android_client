package com.briak.newsclient.model.di.application

import android.content.Context
import com.briak.newsclient.model.data.categories.CategoriesHolder
import com.briak.newsclient.model.data.storage.Preferences
import com.briak.newsclient.model.di.allnews.AllNewsComponent
import com.briak.newsclient.model.di.topnews.TopNewsComponent
import com.briak.newsclient.model.system.AppSchedulers
import com.briak.newsclient.model.system.ResourceManager
import com.briak.newsclient.model.system.SchedulersProvider
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module(subcomponents = [(TopNewsComponent::class), (AllNewsComponent::class)])
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
    fun provideSchedulersProvider(): SchedulersProvider = AppSchedulers()
}