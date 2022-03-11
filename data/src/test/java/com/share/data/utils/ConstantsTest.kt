package com.share.data.utils

import com.share.data.db.entity.DogBreedsEntity
import com.share.data.db.entity.DogBreedsImageEntity
import com.share.data.source.response.ResponseDogImagesData
import com.share.data.source.response.ResponseMessageData

/**
 * @author Juan Sebastian Niño
 */
val dogBreedEntity = listOf(
    DogBreedsEntity(1, "african"),
    DogBreedsEntity(2, "hound", "blood"),
    DogBreedsEntity(3, "hound", "english")
)

val dogBreedImagesEntity = listOf(
    DogBreedsImageEntity(1, 1, "https://images.dog.ceo/breeds/african/n02088094_1001.jpg", false),
    DogBreedsImageEntity(2, 2, "https://images.dog.ceo/breeds/hound-blood/n02088094_1003.jpg", false),
    DogBreedsImageEntity(3, 2, "https://images.dog.ceo/breeds/hound-blood/n02088094_1004.jpg", false),
    DogBreedsImageEntity(4, 3, "https://images.dog.ceo/breeds/hound-english/n02088094_1005.jpg", false)
)

val responseDogBreed = ResponseMessageData(
    mapOf("african" to emptyList(), "hound" to listOf("blood", "english")),
    status = "success"
)

val responseDogImagesBySubBreed = ResponseDogImagesData(
    listOf("https://images.dog.ceo/breeds/hound-blood/n02088094_1003.jpg", "https://images.dog.ceo/breeds/hound-blood/n02088094_1004.jpg")
)

val responseDogImagesByBreed = ResponseDogImagesData(
    listOf("https://images.dog.ceo/breeds/african/n02088094_1001.jpg")
)