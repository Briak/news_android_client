package com.briak.newsclient.model.di.application

import android.content.Context
import com.briak.newsclient.BuildConfig
import com.briak.newsclient.model.data.server.NewsApi
import com.briak.newsclient.model.data.server.interceptor.ErrorResponseInterceptor
import com.briak.newsclient.model.domain.news.NewsInteractor
import com.briak.newsclient.model.domain.news.NewsInteractorImpl
import com.briak.newsclient.model.repositories.news.NewsRepository
import com.briak.newsclient.model.repositories.news.NewsRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideErrorResponseInterceptor(): ErrorResponseInterceptor = ErrorResponseInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
            context: Context,
            errorResponseInterceptor: ErrorResponseInterceptor
    ): OkHttpClient =
            with(OkHttpClient.Builder()) {
                cache(Cache(context.cacheDir, 20 * 1024))
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)

                addNetworkInterceptor(errorResponseInterceptor)
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(
                            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                    )
                }
                build()
            }

    @Provides
    @Singleton
    fun provideRetrofit(
            okHttpClient: OkHttpClient
    ): Retrofit =
            with(Retrofit.Builder()) {
                addCallAdapterFactory(CoroutineCallAdapterFactory())
                addConverterFactory(MoshiConverterFactory.create())
                client(okHttpClient)
                baseUrl("https://newsapi.org")
                build()
            }


    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository = NewsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideNewsInteractor(repository: NewsRepository): NewsInteractor = NewsInteractorImpl(repository)

}