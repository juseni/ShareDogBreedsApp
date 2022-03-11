package com.share.dogbreeds.utils

import com.share.domain.model.DogBreed
import com.share.domain.model.DogBreedImage

val dogBreeds = listOf(
    DogBreed(1, "african", null),
    DogBreed(2, "hound", "blood"),
    DogBreed(3, "hound", "english")
)

val dogImagesBreeds = listOf(
    DogBreedImage(1, "https://images.dog.ceo/breeds/african/n02088094_1001.jpg", false),
    DogBreedImage(2, "https://images.dog.ceo/breeds/hound-blood/n02088094_1003.jpg", true),
    DogBreedImage(3, "https://images.dog.ceo/breeds/hound-blood/n02088094_1004.jpg", false),
    DogBreedImage(3, "https://images.dog.ceo/breeds/hound-english/n02088094_1005.jpg", false)
)