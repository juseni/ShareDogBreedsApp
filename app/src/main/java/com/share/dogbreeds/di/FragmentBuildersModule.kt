package com.share.dogbreeds.di

import com.share.dogbreeds.ui.fragments.DogImagesFragment
import com.share.dogbreeds.ui.fragments.DogSubBreedsFragment
import com.share.dogbreeds.ui.fragments.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Juan Sebastian Niño
 */
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun provideMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun provideDogSubBreedsFragment(): DogSubBreedsFragment

    @ContributesAndroidInjector
    abstract fun provideDogImagesFragment(): DogImagesFragment

}