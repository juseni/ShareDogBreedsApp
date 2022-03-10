package com.share.domain.model

/**
 * @author Juan Sebastian Niño
 */
data class DogBreedImage(
    val dogBreedImageId: Int,
    val imageUrl: String? = null,
    var isFavorite: Boolean = false
)