package com.share.domain.repositories

import com.share.domain.core.Result
import com.share.domain.model.DogBreed
import com.share.domain.model.DogBreedImage
import io.reactivex.rxjava3.core.Single

/**
 * @author Juan Sebastian Niño
 */
interface DogBreedsRepository {

    fun getDogBreeds(): Single<Result<List<DogBreed>>>

    fun getImagesByBreed(doBreedId: Int, dogBreedName: String): Single<Result<List<DogBreedImage>>>

    fun getImagesBySubBreed(
        dogBreedId: Int,
        dogBreedName: String,
        dogSubBreedName: String
    ): Single<Result<List<DogBreedImage>>>

    fun getImagesFavorites(): Single<Result<List<DogBreedImage>>>

    fun setImagesBySubBreed(imageId: Int, isFavorite: Boolean): Single<Result<Boolean>>
}