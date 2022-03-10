package com.share.dogbreeds.viewmodels.states

import com.share.domain.model.DogBreedImage

/**
 * @author Juan Sebastian Niño
 */
sealed class DogBreedsImagesState {

    data class UpdateDogBreeds(val dogBreeds: List<DogBreedImage>) : DogBreedsImagesState()

    object NoImagesToShow : DogBreedsImagesState()

    data class OnError(val exception: Exception) : DogBreedsImagesState()
}