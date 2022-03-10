package com.share.dogbreeds.di

import android.app.Application
import com.share.data.di.DataBaseModule
import com.share.data.di.NetworkModule
import com.share.data.di.RepositoryModule
import com.share.dogbreeds.DogBreedsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        BaseModule::class,
        ExecutorModule::class,
        ActivityBuildersModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        DataBaseModule::class]
)
interface ApplicationComponent : AndroidInjector<DogBreedsApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}