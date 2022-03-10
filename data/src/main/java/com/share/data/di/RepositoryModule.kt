package com.share.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.share.data.db.DogBreedsDataBase
import com.share.data.platform.NetworkHandler
import com.share.data.repositories.DogBreedsRepositoryImpl
import com.share.data.source.DogBreedsApi
import com.share.domain.repositories.DogBreedsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGson(): Gson =
        GsonBuilder().create()

    @Provides
    fun provideDogBreedsRepository(
        apiService: DogBreedsApi,
        gson: Gson,
        dogsBreedDao: DogBreedsDataBase,
        networkChecker: NetworkHandler
    ) : DogBreedsRepository =
        DogBreedsRepositoryImpl(
            gson,
            apiService,
            dogsBreedDao,
            networkChecker
        )
}