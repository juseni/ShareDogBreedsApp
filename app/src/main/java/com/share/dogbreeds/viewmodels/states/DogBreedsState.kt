package com.share.dogbreeds.viewmodels.states

import com.share.domain.model.DogBreed

/**
 * @author Juan Sebastian Niño
 */
sealed class DogBreedsState {

    data class UpdateDogBreeds(val dogBreeds: List<DogBreed>) : DogBreedsState()

    object NoDataFound : DogBreedsState()

    data class OnError(val exception: Exception) : DogBreedsState()
}