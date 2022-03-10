package com.share.dogbreeds.di

import android.app.Application
import android.content.Context
import androidx.navigation.NavController
import com.share.data.repositories.DogBreedsRepositoryImpl
import com.share.domain.executor.PostExecutionThread
import com.share.domain.executor.ThreadExecutor
import com.share.domain.interactor.usecases.DogBreedsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño
 */
@Module
class BaseModule {

    @Provides
    fun provideContext(app: Application): Context = app


    @Singleton
    @Provides
    fun provideNavigatorController(context: Context): NavController {
        return NavController(context)
    }

    @Singleton
    @Provides
    fun provideDogBreedsUsecase(
        repositoryImpl: DogBreedsRepositoryImpl,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ): DogBreedsUseCase {
        return DogBreedsUseCase(repositoryImpl, threadExecutor, postExecutionThread)
    }
}