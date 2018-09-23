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
import com.briak.newsclient.R
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val certificateFactory = CertificateFactory.getInstance("X.509")

        val inputStream = context.resources.openRawResource(R.raw.ssl_certificate)
        val certificate = certificateFactory.generateCertificate(inputStream)
        inputStream.close()

        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", certificate)

        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
        trustManagerFactory.init(keyStore)

        val trustManagers = trustManagerFactory.trustManagers
        val x509TrustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf<TrustManager>(x509TrustManager), null)
        val sslSocketFactory = sslContext.socketFactory

        return with(OkHttpClient.Builder()) {
            cache(Cache(context.cacheDir, 20 * 1024))
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            sslSocketFactory(sslSocketFactory, x509TrustManager)

            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(
                        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }
            build()
        }
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