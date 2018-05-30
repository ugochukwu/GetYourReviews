package com.onwordiesquire.android.getyourreviews.di

import com.google.gson.Gson
import com.onwordiesquire.android.getyourreviews.BuildConfig
import com.onwordiesquire.android.getyourreviews.data.datasource.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = applicationContext {
    bean { provideGson() }
    bean { provideGsonConverterFactory(get()) }
    bean { provideRxJavaAdapterFactory() }
    bean { provideHttpLoggingInterceptor() }
    bean { provideOkHttpClient(get()) }
    bean { provideRetrofit(get(), BASE_URL, get(), get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient,
                    url: String,
                    gsonConverterFactory: GsonConverterFactory,
                    rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
    return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().run {
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
            connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
            build()
        }

fun provideRxJavaAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)
fun provideGson() = Gson()
fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

const val TIMEOUT_DURATION = 60L