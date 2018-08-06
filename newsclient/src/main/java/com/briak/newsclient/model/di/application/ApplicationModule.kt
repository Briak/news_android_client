package com.briak.newsclient.model.di.application

import android.content.Context
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

}