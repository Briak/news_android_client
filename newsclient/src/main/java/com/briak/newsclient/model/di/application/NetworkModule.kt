package com.briak.newsclient.model.di.application

import android.content.Context
import com.briak.newsclient.BuildConfig
import com.briak.newsclient.model.data.server.NewsApi
import com.briak.newsclient.model.data.server.adapters.DateAdapter
import com.briak.newsclient.model.system.ResourceManager
import com.briak.newsclient.presentation.base.ErrorHandler
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient =
            with(OkHttpClient.Builder()) {
                cache(Cache(context.cacheDir, 20 * 1024))
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)

                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(
                            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                    )
                }
                build()
            }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
            .add(Date::class.java, DateAdapter().nullSafe())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
            moshi: Moshi
    ): Retrofit =
            with(Retrofit.Builder()) {
                addCallAdapterFactory(CoroutineCallAdapterFactory())
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(okHttpClient)
                baseUrl("https://newsapi.org")
                build()
            }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
            retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideErrorHandler(resourceManager: ResourceManager) =
            ErrorHandler(resourceManager)

}