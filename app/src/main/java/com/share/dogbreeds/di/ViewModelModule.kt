package com.share.dogbreeds.di

import androidx.lifecycle.ViewModel
import com.share.dogbreeds.viewmodels.DogBreedsImageViewModel
import com.share.dogbreeds.viewmodels.DogBreedsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Juan Sebastian Niño
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DogBreedsViewModel::class)
    abstract fun bindDogBreedsViewModel(dogBreedsViewModel: DogBreedsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DogBreedsImageViewModel::class)
    abstract fun bindDogBreedsImageViewModel(dogBreedsImageViewModel: DogBreedsImageViewModel): ViewModel



}