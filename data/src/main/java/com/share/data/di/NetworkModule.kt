package com.share.data.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.google.gson.Gson
import com.share.data.BuildConfig
import com.share.data.platform.NetworkHandler
import com.share.data.platform.NetworkInterceptor
import com.share.data.source.DogBreedsApi
import com.share.data.utils.TIME_OUT_CONNECTION
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño
 */
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClientBuilder: OkHttpClient.Builder, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClientBuilder.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideDogBreedsApi(retrofit: Retrofit): DogBreedsApi =
        retrofit.create(DogBreedsApi::class.java)

    @Provides
    fun provideOkHttpClientBuilder(networkInterceptor: NetworkInterceptor): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .readTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)

        builder.addInterceptor(networkInterceptor)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder
    }

    @Singleton
    @Provides
    fun provideNetworkHandler(context: Context) =
        NetworkHandler(context)

    @Singleton
    @Provides
    fun provideApolloClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        networkInterceptor: NetworkInterceptor
    ): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BuildConfig.SERVER_URL)
            .okHttpClient(
                okHttpClientBuilder
                    .addInterceptor(networkInterceptor)
                    .build()
            ).build()
    }
}